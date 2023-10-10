package pl.piomin.services.department;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import pl.piomin.services.OrganizationRemoveEvent;
import pl.piomin.services.department.repository.DepartmentRepository;
import pl.piomin.services.OrganizationAddEvent;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentModuleTests {

    @Autowired
    DepartmentRepository repository;

    @Test
    @Order(1)
    void shouldAddDepartmentsOnEvent(Scenario scenario) {
        scenario.publish(new OrganizationAddEvent(1L))
                .andWaitForStateChange(() -> repository.count())
                .andVerify(result -> {assert result.intValue() > 0;});
    }

    @Test
    @Order(2)
    void shouldRemoveDepartmentsOnEvent(Scenario scenario) {
        scenario.publish(new OrganizationRemoveEvent(1L))
                .andWaitForStateChange(() -> repository.count())
                .andVerify(result -> {assert result.intValue() == 0;});
    }
}
