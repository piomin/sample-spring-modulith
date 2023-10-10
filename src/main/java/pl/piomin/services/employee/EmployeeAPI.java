package pl.piomin.services.employee;

import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeAPI {

    List<EmployeeDTO> getEmployeesByDepartmentId(Long id);
    List<EmployeeDTO> getEmployeesByOrganizationId(Long id);

}
