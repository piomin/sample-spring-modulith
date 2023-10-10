package pl.piomin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.department.management.DepartmentManagement;
import pl.piomin.services.department.model.Department;
import pl.piomin.services.employee.management.EmployeeManagement;
import pl.piomin.services.employee.model.Employee;
import pl.piomin.services.organization.OrganizationDTO;
import pl.piomin.services.organization.management.OrganizationManagement;
import pl.piomin.services.organization.model.Organization;

@SpringBootApplication
@RestController
public class SpringModulith {

    public static void main(String[] args) {
        SpringApplication.run(SpringModulith.class, args);
    }

    @Autowired
    DepartmentManagement departmentManagement;
    @Autowired
    EmployeeManagement employeeManagement;
    @Autowired
    OrganizationManagement organizationManagement;

    @GetMapping("/departments/{id}/with-employees")
    public DepartmentDTO apiDepartmentWithEmployees(@PathVariable("id") Long id) {
        return departmentManagement.getDepartmentByIdWithEmployees(id);
    }

    @GetMapping("/organizations/{id}/with-departments")
    public OrganizationDTO apiOrganizationWithDepartments(@PathVariable("id") Long id) {
        return organizationManagement.findByIdWithDepartments(id);
    }

    @PostMapping("/organizations")
    public Organization apiAddOrganization(@RequestBody Organization o) {
        return organizationManagement.add(o);
    }

    @PostMapping("/employees")
    public Employee apiAddEmployee(@RequestBody Employee employee) {
        return employeeManagement.add(employee);
    }

    @PostMapping("/departments")
    public Department apiAddDepartment(@RequestBody Department department) {
        return departmentManagement.add(department);
    }
}