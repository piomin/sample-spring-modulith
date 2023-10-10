package pl.piomin.services.organization.management;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.department.DepartmentAPI;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.employee.EmployeeAPI;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.OrganizationAddEvent;
import pl.piomin.services.organization.OrganizationDTO;
import pl.piomin.services.organization.model.Organization;
import pl.piomin.services.organization.repository.OrganizationRepository;

import java.util.List;

@Service
public class OrganizationManagement {

    private final ApplicationEventPublisher events;
    private final OrganizationRepository repository;
    private final DepartmentAPI departmentAPI;
    private final EmployeeAPI employeeAPI;

    public OrganizationManagement(ApplicationEventPublisher events,
                                  OrganizationRepository repository,
                                  DepartmentAPI departmentAPI,
                                  EmployeeAPI employeeAPI) {
        this.events = events;
        this.repository = repository;
        this.departmentAPI = departmentAPI;
        this.employeeAPI = employeeAPI;
    }

    public OrganizationDTO findByIdWithEmployees(Long id) {
        OrganizationDTO dto = repository.findDTOById(id);
        List<EmployeeDTO> dtos = employeeAPI.getEmployeesByOrganizationId(id);
        dto.employees().addAll(dtos);
        return dto;
    }

    public OrganizationDTO findByIdWithDepartments(Long id) {
        OrganizationDTO dto = repository.findDTOById(id);
        List<DepartmentDTO> dtos = departmentAPI.getDepartmentsByOrganizationId(id);
        dto.departments().addAll(dtos);
        return dto;
    }

    @Transactional
    public Organization add(Organization organization) {
        Organization o = repository.save(organization);
        events.publishEvent(new OrganizationAddEvent(o.getId()));
        return o;
    }

}
