package pl.piomin.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.employee.EmployeeDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppRestControllerTests {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @Order(1)
    void shouldAddNewEmployee() {
        EmployeeDTO emp = new EmployeeDTO(null, 1L, 1L, "Test", 30, "HR");
        EmployeeDTO result = webTestClient.post()
                .uri("/api/employees")
                .bodyValue(emp)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .returnResult().getResponseBody();
        assertNotNull(result);
        assertNotNull(result.id());
    }

    @Test
    @Order(1)
    void shouldAddNewDepartment() {
        DepartmentDTO dep = new DepartmentDTO(null, 1L, "Test");
        DepartmentDTO result = webTestClient.post()
                .uri("/api/departments")
                .bodyValue(dep)
                .exchange()
                .expectStatus().isOk()
                .expectBody(DepartmentDTO.class)
                .returnResult().getResponseBody();
        assertNotNull(result);
        assertNotNull(result.id());
    }

    @Test
    @Order(2)
    void shouldFindDepartmentWithEmployees() {
        DepartmentDTO dep = webTestClient.get()
                .uri("/api/departments/{id}/with-employees", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(DepartmentDTO.class)
                .returnResult().getResponseBody();
        assertNotNull(dep);
        assertNotNull(dep.id());
    }
}
