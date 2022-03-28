package ru.rodionov.spring;


import liquibase.pro.packaged.A;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import ru.rodionov.spring.DTO.LoginDTO;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.enums.UserRole;
import ru.rodionov.spring.model.User;
import ru.rodionov.spring.repository.UserRepository;
import ru.rodionov.spring.service.UserService;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = IntegrationTest.Initializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    protected String port;


    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @BeforeEach
    public void before(){
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE;");
    }


    @Test
    public void loginTest() {
        User us = new User();
        us.setLogin("admin");
        us.setName("admin");
        us.setRole(UserRole.ADMIN);
        us.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(us);

        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + port).build();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("admin");
        loginDTO.setPassword("admin");
        HttpEntity<LoginDTO> tHttpEntity = new HttpEntity<>(loginDTO,new HttpHeaders());
        ResponseEntity<String> exchange = restTemplate.exchange("/login", HttpMethod.POST, tHttpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        Assertions.assertNotNull(exchange.getBody());
    }
    @Test
    public void login401Test() {
        User us = new User();
        us.setLogin("admin");
        us.setName("admin");
        us.setRole(UserRole.ADMIN);
        us.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(us);

        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + port).build();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("admin");
        loginDTO.setPassword("admin2");
        HttpEntity<LoginDTO> tHttpEntity = new HttpEntity<>(loginDTO,new HttpHeaders());
        ResponseEntity<String> exchange = restTemplate.exchange("/login", HttpMethod.POST, tHttpEntity, String.class);
        //ResponseEntity<String> exchange = testRestTemplate.
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exchange.getStatusCode());
       // Assertions.assertNotNull(exchange.getBody());
    }
    // перевести все в тесты
    //в конце теста залесть в базу по userRepository и проверить наличие

    @Test
    public void create(){
        User us = new User();
        us.setLogin("admin");
        us.setName("admin");
        us.setRole(UserRole.ADMIN);
        us.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(us);

        RestTemplate restTemplate = restTemplateBuilder.rootUri("http://localhost:" + port).build();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("admin");
        loginDTO.setPassword("admin");
        HttpEntity<LoginDTO> tHttpEntity = new HttpEntity<>(loginDTO,new HttpHeaders());
        ResponseEntity<String> exchange = restTemplate.exchange("/login", HttpMethod.POST, tHttpEntity, String.class);
        String header = exchange.getBody();

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("manager");
        userDTO.setName("manager");
        userDTO.setLogin("manager");
        userDTO.setRole(UserRole.MANAGER);

        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of("Bearer " + header));

        HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange("/user", HttpMethod.POST, userDTOHttpEntity, UserDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getId());
    }




    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PostgresqlTestContainer.applyForLiquibase(applicationContext, "public");
        }

    }
}
