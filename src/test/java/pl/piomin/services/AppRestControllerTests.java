package pl.piomin.services;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.piomin.services.employee.model.Employee;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppRestControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void shouldAddNewEmployee() {
        Employee emp = new Employee();
        emp.setAge(30);
        emp.setDepartmentId(1L);
        emp.setOrganizationId(1L);
        emp.setPosition("HR");
        emp.setName("Test");
        emp = restTemplate.postForObject("/employees", emp, Employee.class);
        assertNotNull(emp.getId());
    }
}
