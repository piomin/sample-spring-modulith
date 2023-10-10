package pl.piomin.services.organization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.piomin.services.organization.OrganizationDTO;
import pl.piomin.services.organization.model.Organization;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {
    OrganizationDTO organizationToOrganizationDTO(Organization organization);
    Organization organizationDTOToOrganization(OrganizationDTO organizationDTO);
}
