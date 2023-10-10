package pl.piomin.services.employee.repository;

import org.springframework.data.repository.CrudRepository;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.employee.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<EmployeeDTO> findByDepartmentId(Long departmentId);
    List<EmployeeDTO> findByOrganizationId(Long organizationId);
    void deleteByOrganizationId(Long organizationId);

}
