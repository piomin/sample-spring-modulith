implementation(platform("org.springframework.modulith:spring-modulith-bom:1.4"))</newLines>

<rationale>[src/main/java/pl/piomin/services/config/ModulithConfiguration.java] Created a configuration class to register the new event-metrics customizer, tagging metrics with application name and version.</rationale>
<newLines>package pl.piomin.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.observability.ModulithEventMetricsCustomizer;

@Configuration
public class ModulithConfiguration {

    @Bean
    public ModulithEventMetricsCustomizer eventMetricsCustomizer() {
        return metrics -> metrics
            .tag("application", "sample-spring-modulith")
            .tag("version", "1.4");
    }
}
</newLines>

<rationale>[src/test/java/pl/piomin/services/config/TestConfiguration.java] Added a test configuration class to document that AssertablePublishedEvents support is automatically provided in Spring Modulith 1.4 when AssertJ is on the classpath.</rationale>
<newLines>package pl.piomin.services.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.modulith.test.PublishedEvents;
import org.springframework.modulith.test.AssertablePublishedEvents;

@TestConfiguration
public class TestConfiguration {

    // Spring Modulith 1.4 automatically provides AssertablePublishedEvents 
    // when AssertJ is on the classpath. This configuration is for documentation.
}
</newLines>

<rationale>[src/main/java/pl/piomin/services/config/ObservabilityConfiguration.java] Created an observability configuration to register the default Modulith observation convention for standardized naming of observations.</rationale>
<newLines>package pl.piomin.services.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.observability.DefaultModulithObservationConvention;

@Configuration
public class ObservabilityConfiguration {

    @Bean
    public DefaultModulithObservationConvention modulithObservationConvention() {
        return new DefaultModulithObservationConvention();
    }
}
</newLines>

<rationale>[src/test/java/pl/piomin/services/SpringModulithTests.java] Extended the Documenter invocation to also generate module canvases and added a new test to print module metadata for verification.</rationale>
<newLines>    // Updated Documenter call to include module canvases
    new Documenter(modules)
        .writeDocumentation()
        .writeModuleCanvases();

    // New test method to expose module information
    @Test
    void shouldExposeModuleInformation() {
        modules.forEach(module -> {
            System.out.println("Module: " + module.getName());
            System.out.println("Base package: " + module.getBasePackage());
            System.out.println("Internal types exposed: " + !module.getBootstrapDependencies().isEmpty());
        });
    }
</newLines>

<rationale>[src/main/resources/application.yml] Appended new observability and management settings to enable event externalization logging, full endpoint exposure, and default metric/tracing tags.</rationale>
<newLines>modulith:
  observability:
    events:
      externalization:
        logging:
          enabled: true

management:
  endpoints:
    web:
      exposure:
        include: "*"
  metrics:
    tags:
      application: sample-spring-modulith
      version: "1.4"
  tracing:
    sampling:
      probability: 1.0