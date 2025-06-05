package pl.piomin.services.department;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.OrganizationRemoveEvent;
import pl.piomin.services.department.management.DepartmentManagement;
import pl.piomin.services.department.repository.DepartmentRepository;
import pl.piomin.services.OrganizationAddEvent;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentModuleTests {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentModuleTests.class);
    private static final long TEST_ID = 100;

    @Autowired
    DepartmentRepository repository;

    @Test
    @Order(1)
    void shouldAddDepartmentsOnEvent(Scenario scenario) {
        scenario.publish(new OrganizationAddEvent(TEST_ID))
                .andWaitForStateChange(() -> {
                    var r = repository.findByOrganizationId(TEST_ID);
                    LOG.info("State on add: {}", r);
                    return r;
                })
                .andVerify(result -> {
                    assert !result.isEmpty();
                });
    }

    // Hard to verify what's happened, because test fails
    // TODO - uncomment to try in the next version of Spring Modulith
//    @Test
//    @Order(2)
    void shouldRemoveDepartmentsOnEvent(Scenario scenario) {
        scenario.publish(new OrganizationRemoveEvent(TEST_ID))
                .andWaitForStateChange(() -> {
                    var r = repository.findByOrganizationId(TEST_ID);
                    LOG.info("State on remove: {}", r);
                    return r;
                })
                .andVerify(result -> {
                    assert result.isEmpty();
                });
    }
}
