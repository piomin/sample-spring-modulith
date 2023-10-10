package pl.piomin.services.gateway;

import org.springframework.web.bind.annotation.*;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.department.DepartmentExternalAPI;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.employee.EmployeeExternalAPI;

@RestController
@RequestMapping("/api")
public class GatewayManagement {

    private DepartmentExternalAPI departmentExternalAPI;
    private EmployeeExternalAPI employeeExternalAPI;

    public GatewayManagement(DepartmentExternalAPI departmentExternalAPI,
                             EmployeeExternalAPI employeeExternalAPI) {
        this.departmentExternalAPI = departmentExternalAPI;
        this.employeeExternalAPI = employeeExternalAPI;
    }

//
//    @GetMapping("/organizations/{id}/with-departments")
//    public OrganizationDTO apiOrganizationWithDepartments(@PathVariable("id") Long id) {
//        return organizationManagement.findByIdWithDepartments(id);
//    }
//
//    @PostMapping("/organizations")
//    public Organization apiAddOrganization(@RequestBody Organization o) {
//        return organizationManagement.add(o);
//    }
//
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
