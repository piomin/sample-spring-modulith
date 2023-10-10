package pl.piomin.services.department.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import pl.piomin.services.OrganizationAddEvent;
import pl.piomin.services.OrganizationRemoveEvent;
import pl.piomin.services.department.DepartmentInternalAPI;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.department.DepartmentExternalAPI;
import pl.piomin.services.department.mapper.DepartmentMapper;
import pl.piomin.services.department.repository.DepartmentRepository;
import pl.piomin.services.employee.EmployeeInternalAPI;
import pl.piomin.services.employee.EmployeeDTO;

import java.util.List;

@Service
public class DepartmentManagement implements DepartmentInternalAPI, DepartmentExternalAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentManagement.class);
    private final ApplicationEventPublisher events;
    private DepartmentRepository repository;
    private EmployeeInternalAPI employeeInternalAPI;
    private DepartmentMapper mapper;

    public DepartmentManagement(DepartmentRepository repository,
                                ApplicationEventPublisher events,
                                EmployeeInternalAPI employeeInternalAPI,
                                DepartmentMapper mapper) {
        this.repository = repository;
        this.events = events;
        this.employeeInternalAPI = employeeInternalAPI;
        this.mapper = mapper;
    }

    @Override
    public DepartmentDTO getDepartmentByIdWithEmployees(Long id) {
        DepartmentDTO d = repository.findDTOById(id);
        List<EmployeeDTO> dtos = employeeInternalAPI.getEmployeesByDepartmentId(id);
        d.employees().addAll(dtos);
        return d;
    }

    @ApplicationModuleListener
    void onNewOrganizationEvent(OrganizationAddEvent event) {
        LOG.info("onNewOrganizationEvent(orgId={})", event.getId());
        add(new DepartmentDTO(null, event.getId(), "HR"));
        add(new DepartmentDTO(null, event.getId(), "Management"));
    }

    @ApplicationModuleListener
    void onRemovedOrganizationEvent(OrganizationRemoveEvent event) {
        LOG.info("onRemovedOrganizationEvent(orgId={})", event.getId());
        repository.deleteByOrganizationId(event.getId());
    }

    @Override
    public DepartmentDTO add(DepartmentDTO department) {
        return mapper.departmentToEmployeeDTO(
                repository.save(mapper.departmentDTOToEmployee(department))
        );
    }

    @Override
    public List<DepartmentDTO> getDepartmentsByOrganizationId(Long id) {
        return repository.findByOrganizationId(id);
    }
}
