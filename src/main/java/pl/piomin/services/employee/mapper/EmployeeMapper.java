package pl.piomin.services.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.piomin.services.employee.EmployeeDTO;
import pl.piomin.services.employee.model.Employee;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO employeeToEmployeeDTO(Employee employee);
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
