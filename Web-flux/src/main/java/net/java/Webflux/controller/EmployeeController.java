package net.java.Webflux.controller;

import lombok.AllArgsConstructor;
import net.java.Webflux.dto.EmployeeDto;
import net.java.Webflux.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }
    @GetMapping("{id}")
    public ResponseEntity<Mono<EmployeeDto>> getEmployee(@PathVariable("id") String employeeId) {
               Mono<EmployeeDto> getEmp = employeeService.getEmployee(employeeId);
               return ResponseEntity.ok(getEmp);
    }
    @GetMapping
    public Flux<EmployeeDto> getAll() {
        return employeeService.getAll();

    }
    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,@PathVariable("id") String id){
        return employeeService.updateEmployee(employeeDto, id);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") String id) {
        return employeeService.deleteEmployee(id);
    }


    }
