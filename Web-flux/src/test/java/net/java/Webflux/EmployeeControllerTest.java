package net.java.Webflux;

import net.java.Webflux.dto.EmployeeDto;
import net.java.Webflux.repository.EmployeeRepository;
import net.java.Webflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
     private EmployeeRepository employeeRepository;

    @BeforeEach
    public void before(){
        System.out.println("Before Each Test");
        employeeRepository.deleteAll().subscribe();
    }

    @Test
    public void testSaveEmployee() {
        EmployeeDto employeeDto = new EmployeeDto("Kenan", "Kenanov", "kenan@gmail.com");
        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(employeeDto.name())
                .jsonPath("$.lastName").isEqualTo(employeeDto.lastName())
                .jsonPath("$.email").isEqualTo(employeeDto.email());


    }
    @Test
    public void testGetEmployee(){
        EmployeeDto employeeDto = new EmployeeDto("Fazil","Fazilov","fazil@gmail.com");
        EmployeeDto saveEmployee = employeeService.saveEmployee(employeeDto).block();
        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id",saveEmployee.name()))//getId() lazimdi
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(employeeDto.name())
                .jsonPath("$.lastName").isEqualTo(employeeDto.lastName())
                .jsonPath("$.email").isEqualTo(employeeDto.email());


    }
    @Test
    public void getAllEmployees() {
        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);

    }

}
