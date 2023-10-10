package pl.piomin.services.organization.management;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.department.DepartmentInternalAPI;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.employee.EmployeeInternalAPI;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.OrganizationAddEvent;
import pl.piomin.services.organization.OrganizationDTO;
import pl.piomin.services.organization.mapper.OrganizationMapper;
import pl.piomin.services.organization.model.Organization;
import pl.piomin.services.organization.repository.OrganizationRepository;

import java.util.List;

@Service
public class OrganizationManagement {

    private final ApplicationEventPublisher events;
    private final OrganizationRepository repository;
    private final DepartmentInternalAPI departmentInternalAPI;
    private final EmployeeInternalAPI employeeInternalAPI;
    private final OrganizationMapper mapper;

    public OrganizationManagement(ApplicationEventPublisher events,
                                  OrganizationRepository repository,
                                  DepartmentInternalAPI departmentInternalAPI,
                                  EmployeeInternalAPI employeeInternalAPI,
                                  OrganizationMapper mapper) {
        this.events = events;
        this.repository = repository;
        this.departmentInternalAPI = departmentInternalAPI;
        this.employeeInternalAPI = employeeInternalAPI;
        this.mapper = mapper;
    }

    public OrganizationDTO findByIdWithEmployees(Long id) {
        OrganizationDTO dto = repository.findDTOById(id);
        List<EmployeeDTO> dtos = employeeInternalAPI.getEmployeesByOrganizationId(id);
        dto.employees().addAll(dtos);
        return dto;
    }

    public OrganizationDTO findByIdWithDepartments(Long id) {
        OrganizationDTO dto = repository.findDTOById(id);
        List<DepartmentDTO> dtos = departmentInternalAPI.getDepartmentsByOrganizationId(id);
        dto.departments().addAll(dtos);
        return dto;
    }

    @Transactional
    public OrganizationDTO add(OrganizationDTO organization) {
        OrganizationDTO dto = mapper.organizationToOrganizationDTO(
                repository.save(mapper.organizationDTOToOrganization(organization))
        );
        events.publishEvent(new OrganizationAddEvent(dto.id()));
        return dto;
    }

}
