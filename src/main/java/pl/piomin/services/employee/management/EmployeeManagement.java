package pl.piomin.services.employee.management;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.employee.EmployeeInternalAPI;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.employee.EmployeeExternalAPI;
import pl.piomin.services.employee.mapper.EmployeeMapper;
import pl.piomin.services.employee.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeManagement implements EmployeeInternalAPI, EmployeeExternalAPI {

    private EmployeeRepository repository;
    private EmployeeMapper mapper;

    public EmployeeManagement(EmployeeRepository repository,
                              EmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
        return mapper.employeeToEmployeeDTO(
                repository.save(mapper.employeeDTOToEmployee(employee))
        );
    }

}
