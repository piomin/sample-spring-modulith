package pl.piomin.services.employee;

import java.util.List;

public interface EmployeeInternalAPI {

    List<EmployeeDTO> getEmployeesByDepartmentId(Long id);
    List<EmployeeDTO> getEmployeesByOrganizationId(Long id);

}
