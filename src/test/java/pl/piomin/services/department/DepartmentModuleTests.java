package pl.piomin.services.department;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import pl.piomin.services.department.repository.DepartmentRepository;
import pl.piomin.services.OrganizationAddEvent;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
public class DepartmentModuleTests {

    @Autowired
    DepartmentRepository repository;

    @Test
    void shouldAddDepartmentsOnEvent(Scenario scenario) {
        scenario.publish(new OrganizationAddEvent(1L))
                .andWaitForStateChange(() -> repository.count())
                .andVerify(result -> {assert result.intValue() > 0;});
    }
}
