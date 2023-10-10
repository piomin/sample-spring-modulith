package pl.piomin.services.department;

import java.util.List;

public interface DepartmentAPI {

    List<DepartmentDTO> getDepartmentsByOrganizationId(Long id);
}
