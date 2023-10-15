package net.java.Webflux.service.imple;

import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import net.java.Webflux.dto.EmployeeDto;
import net.java.Webflux.entity.Employee;
import net.java.Webflux.mapper.EmployeeMapper;
import net.java.Webflux.repository.EmployeeRepository;
import net.java.Webflux.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImple implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


//    @Override
//    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
//        Employee employee = employeeMapper.toEmployee(employeeDto);
//        Mono<Employee> saveEmployee = employeeRepository.save(employee);
//        return saveEmployee.map((employees) -> employeeMapper.toEmployeeDto(employee));
//    }

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEmployee(employeeDto);
        Mono<Employee> saveEmployee= employeeRepository.save(employee);
        return saveEmployee.map((employees)->employeeMapper.toEmployeeDto(employees));
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String employeeId) {
        Mono<Employee> getEmployeeMono = employeeRepository.findById(employeeId);
        return getEmployeeMono.map((employees)->employeeMapper.toEmployeeDto(employees));
    }


    @Override
    public Flux<EmployeeDto> getAll() {
        Flux<Employee> employeeFlux = employeeRepository.findAll();
         return employeeFlux.map((employees)->employeeMapper.toEmployeeDto(employees))
                 .switchIfEmpty(Flux.empty());
    }


//    @Override
//    public Flux<EmployeeDto> getAll() {
//        Flux<Employee> employeeFlux = employeeRepository.findAll();
//        return employeeFlux.map((employees)->employeeMapper.toEmployeeDto(employees)).
//                switchIfEmpty(Flux.empty());
//    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String id) {
        Mono<Employee> updateEmployee = employeeRepository.findById(id);
        Mono<Employee> saveEmployeeMono = updateEmployee.flatMap((existing)->{
            existing.setName(existing.getName());
            existing.setLastName(existing.getLastName());
            existing.setEmail(existing.getEmail());
            return employeeRepository.save(existing);
        });
        return saveEmployeeMono.map((employees)->employeeMapper.toEmployeeDto(employees));



    }

    @Override
    public Mono<Void> deleteEmployee(String id) {
        return employeeRepository.deleteById(id);
    }


}