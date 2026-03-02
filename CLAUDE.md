# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
mvn clean package

# Run application (requires Docker for Zipkin)
mvn spring-boot:run

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=AppRestControllerTests

# Run a single test method
mvn test -Dtest=AppRestControllerTests#shouldAddNewEmployee
```

## Architecture

A **modular monolith** built with Spring Boot 4 and Spring Modulith. The four modules live under `pl.piomin.services`:

| Module | Role |
|---|---|
| `department` | Manages `Department` entity |
| `employee` | Manages `Employee` entity |
| `organization` | Manages `Organization` entity; the central module with no dependencies |
| `gateway` | Single `@RestController` (`GatewayManagement`) that delegates to all three domain modules |

### Module boundary rules

Each domain module exposes two interface types:
- `*ExternalAPI` — consumed by the `gateway` module via `@Autowired`
- `*InternalAPI` — consumed by other domain modules for cross-module calls

The concrete `*Management` classes implement both interfaces. The `gateway` module must only reference `*ExternalAPI` types, never internal classes or repositories from other modules.

### Inter-module events

Modules communicate asynchronously via application events declared in the root package:
- `OrganizationAddEvent` — triggers `DepartmentManagement` to auto-create default departments
- `OrganizationRemoveEvent` — triggers cleanup in `DepartmentManagement` and `EmployeeManagement`

Event listeners use `@ApplicationModuleListener` (Spring Modulith's transactional event listener).

### Technology stack

- **Persistence**: Spring Data JPA + H2 (runtime); repositories extend `CrudRepository`
- **Mapping**: MapStruct (`@Mapper(componentModel = "spring")`); annotation processor configured in `maven-compiler-plugin`
- **API docs**: springdoc-openapi (webmvc-ui)
- **Tracing**: Micrometer + OpenTelemetry → Zipkin (`docker-compose.yml` provides the container)
- **Testing**: `WebTestClient` for REST integration tests (requires `spring-boot-starter-webflux` on test classpath); `@ApplicationModuleTest` with `Scenario` for isolated module tests

### Testing patterns

- `@SpringBootTest(webEnvironment = RANDOM_PORT)` + `@Autowired WebTestClient` — full-stack REST tests (`AppRestControllerTests`)
- `@ApplicationModuleTest(BootstrapMode.DIRECT_DEPENDENCIES)` + `Scenario` — Spring Modulith slice tests that start only the module under test and its direct dependencies (`DepartmentModuleTests`, `EmployeeModuleTests`)
- `SpringModulithTests` — verifies module structure rules and generates documentation via `ApplicationModules.verify()`
