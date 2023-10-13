package pl.piomin.services.organization;

public interface OrganizationExternalAPI {

    OrganizationDTO findByIdWithEmployees(Long id);
    OrganizationDTO findByIdWithDepartments(Long id);
    OrganizationDTO findByIdWithDepartmentsAndEmployees(Long id);
    OrganizationDTO add(OrganizationDTO organization);
    void remove(Long id);

}
