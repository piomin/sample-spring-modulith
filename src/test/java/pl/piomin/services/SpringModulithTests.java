package pl.piomin.services;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class SpringModulithTests {

    @Test
    void shouldBeCompliant() {
        ApplicationModules.of(SpringModulith.class).verify();
    }
}
