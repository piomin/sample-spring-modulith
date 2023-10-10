package pl.piomin.services.department.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import pl.piomin.services.OrganizationAddEvent;
import pl.piomin.services.department.DepartmentAPI;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.department.DepartmentExternalAPI;
import pl.piomin.services.department.mapper.DepartmentMapper;
import pl.piomin.services.department.repository.DepartmentRepository;
import pl.piomin.services.employee.EmployeeAPI;
import pl.piomin.services.employee.EmployeeDTO;

import java.util.List;

@Service
public class DepartmentManagement implements DepartmentAPI, DepartmentExternalAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentManagement.class);
    private final ApplicationEventPublisher events;
    private DepartmentRepository repository;
    private EmployeeAPI employeeAPI;

    public DepartmentManagement(DepartmentRepository repository,
                                ApplicationEventPublisher events,
                                EmployeeAPI employeeAPI) {
        this.repository = repository;
        this.events = events;
        this.employeeAPI = employeeAPI;
    }

    public DepartmentDTO getDepartmentByIdWithEmployees(Long id) {
        DepartmentDTO d = repository.findDTOById(id);
        List<EmployeeDTO> dtos = employeeAPI.getEmployeesByDepartmentId(id);
        d.employees().addAll(dtos);
        return d;
    }

    @ApplicationModuleListener
    void onNewOrganizationEvent(OrganizationAddEvent event) {
        LOG.info("onNewOrganizationEvent(orgId={})", event.getId());
        add(new DepartmentDTO(null, event.getId(), "HR"));
        add(new DepartmentDTO(null, event.getId(), "Management"));
    }

    public DepartmentDTO add(DepartmentDTO department) {
        return DepartmentMapper.INSTANCE.departmentToEmployeeDTO(repository.save(DepartmentMapper.INSTANCE.departmentDTOToEmployee(department)));
    }

    @Override
    public List<DepartmentDTO> getDepartmentsByOrganizationId(Long id) {
        return repository.findByOrganizationId(id);
    }
}
