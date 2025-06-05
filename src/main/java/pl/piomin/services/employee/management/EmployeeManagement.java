package pl.piomin.services.employee.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.OrganizationRemoveEvent;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.employee.EmployeeExternalAPI;
import pl.piomin.services.employee.EmployeeInternalAPI;
import pl.piomin.services.employee.mapper.EmployeeMapper;
import pl.piomin.services.employee.model.Employee;
import pl.piomin.services.employee.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeManagement implements EmployeeInternalAPI, EmployeeExternalAPI {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeManagement.class);
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

    @Override
    @Transactional
    public EmployeeDTO add(EmployeeDTO employee) {
        Employee emp = mapper.employeeDTOToEmployee(employee);
        return mapper.employeeToEmployeeDTO(repository.save(emp));
    }

    @ApplicationModuleListener
    void onRemovedOrganizationEvent(OrganizationRemoveEvent event) {
        LOG.info("onRemovedOrganizationEvent(orgId={})", event.getId());
        repository.deleteByOrganizationId(event.getId());
    }

}
