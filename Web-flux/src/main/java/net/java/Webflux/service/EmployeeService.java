package net.java.Webflux.service;

import net.java.Webflux.dto.EmployeeDto;
import net.java.Webflux.entity.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface EmployeeService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);
    Mono<EmployeeDto> getEmployee(String employeeId);
    Flux<EmployeeDto> getAll();
    Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto,String id);
    Mono<Void> deleteEmployee(String id);




}
