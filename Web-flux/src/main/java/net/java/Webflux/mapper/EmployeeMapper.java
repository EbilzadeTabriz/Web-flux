package net.java.Webflux.mapper;

import net.java.Webflux.dto.EmployeeDto;
import net.java.Webflux.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toEmployeeDto(Employee employee);
    Employee toEmployee(EmployeeDto employeeDto);

}

