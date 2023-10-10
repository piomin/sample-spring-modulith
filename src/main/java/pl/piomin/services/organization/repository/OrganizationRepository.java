package pl.piomin.services.organization.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.piomin.services.organization.OrganizationDTO;
import pl.piomin.services.organization.model.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    @Query("""
           SELECT new pl.piomin.services.organization.OrganizationDTO(o.id, o.name, o.address)
           FROM Organization o
           WHERE o.id = :id
           """)
    OrganizationDTO findDTOById(Long id);
}
