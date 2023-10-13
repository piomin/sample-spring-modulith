package pl.piomin.services.department;

import java.util.List;

public interface DepartmentInternalAPI {

    List<DepartmentDTO> getDepartmentsByOrganizationId(Long id);
    List<DepartmentDTO> getDepartmentsByOrganizationIdWithEmployees(Long id);
}
