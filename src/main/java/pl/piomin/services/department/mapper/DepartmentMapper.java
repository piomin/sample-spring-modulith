package pl.piomin.services.department.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.piomin.services.department.DepartmentDTO;
import pl.piomin.services.department.model.Department;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentDTO departmentToEmployeeDTO(Department department);
    Department departmentDTOToEmployee(DepartmentDTO departmentDTO);
}
