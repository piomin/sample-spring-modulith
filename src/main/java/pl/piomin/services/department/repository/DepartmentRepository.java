package pl.piomin.services.department.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.department.model.Department;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    @Query("""
           SELECT new pl.piomin.services.department.DepartmentDTO(d.id, d.organizationId, d.name)
           FROM Department d
           WHERE d.id = :id
           """)
    DepartmentDTO findDTOById(Long id);

    @Query("""
           SELECT new pl.piomin.services.department.DepartmentDTO(d.id, d.organizationId, d.name)
           FROM Department d
           WHERE d.organizationId = :organizationId
           """)
    List<DepartmentDTO> findByOrganizationId(Long organizationId);

    void deleteByOrganizationId(Long organizationId);
}
