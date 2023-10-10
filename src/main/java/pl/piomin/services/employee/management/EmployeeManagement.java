package pl.piomin.services.employee.management;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.employee.EmployeeAPI;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.employee.EmployeeExternalAPI;
import pl.piomin.services.employee.mapper.EmployeeMapper;
import pl.piomin.services.employee.model.Employee;
import pl.piomin.services.employee.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeManagement implements EmployeeAPI, EmployeeExternalAPI {

    private EmployeeRepository repository;

    public EmployeeManagement(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByOrganizationId(Long id) {
        return repository.findByOrganizationId(id);
    }

    @Transactional
    public EmployeeDTO add(EmployeeDTO employee) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeDTO(
                repository.save(EmployeeMapper.INSTANCE.employeeDTOToEmployee(employee))
        );
    }

}
