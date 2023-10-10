package pl.piomin.services.gateway;

import org.springframework.web.bind.annotation.*;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.department.DepartmentExternalAPI;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.employee.EmployeeExternalAPI;
import pl.piomin.services.organization.OrganizationDTO;
import pl.piomin.services.organization.OrganizationExternalAPI;

@RestController
@RequestMapping("/api")
public class GatewayManagement {

    private DepartmentExternalAPI departmentExternalAPI;
    private EmployeeExternalAPI employeeExternalAPI;
    private OrganizationExternalAPI organizationExternalAPI;

    public GatewayManagement(DepartmentExternalAPI departmentExternalAPI,
                             EmployeeExternalAPI employeeExternalAPI,
                             OrganizationExternalAPI organizationExternalAPI) {
        this.departmentExternalAPI = departmentExternalAPI;
        this.employeeExternalAPI = employeeExternalAPI;
        this.organizationExternalAPI = organizationExternalAPI;
    }


    @GetMapping("/organizations/{id}/with-departments")
    public OrganizationDTO apiOrganizationWithDepartments(@PathVariable("id") Long id) {
        return organizationExternalAPI.findByIdWithDepartments(id);
    }

    @PostMapping("/organizations")
    public OrganizationDTO apiAddOrganization(@RequestBody OrganizationDTO o) {
        return organizationExternalAPI.add(o);
    }

    @PostMapping("/employees")
    public EmployeeDTO apiAddEmployee(@RequestBody EmployeeDTO employee) {
        return employeeExternalAPI.add(employee);
    }

    @GetMapping("/departments/{id}/with-employees")
    public DepartmentDTO apiDepartmentWithEmployees(@PathVariable("id") Long id) {
        return departmentExternalAPI.getDepartmentByIdWithEmployees(id);
    }

    @PostMapping("/departments")
    public DepartmentDTO apiAddDepartment(@RequestBody DepartmentDTO department) {
        return departmentExternalAPI.add(department);
    }
}
