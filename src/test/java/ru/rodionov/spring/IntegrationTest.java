package ru.rodionov.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rodionov.spring.DTO.LoginDTO;
import ru.rodionov.spring.enums.UserRole;
import ru.rodionov.spring.model.User;
import ru.rodionov.spring.repository.UserRepository;
import ru.rodionov.spring.service.UserService;

import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = IntegrationTest.Initializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected TestRestTemplate testRestTemplate;
    @LocalServerPort
    protected String port;


    @BeforeEach
    public void before() {
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE;");
    }

    protected HttpHeaders getAdminHeader(){
        User us = new User();
        us.setLogin("admin");
        us.setName("admin");
        us.setRole(UserRole.ADMIN);
        us.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(us);


        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("admin");
        loginDTO.setPassword("admin");
        HttpEntity<LoginDTO> tHttpEntity = new HttpEntity<>(loginDTO,new HttpHeaders());
        ResponseEntity<String> exchange = testRestTemplate.exchange("/login", HttpMethod.POST, tHttpEntity, String.class);
        String header = exchange.getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of("Bearer " + header));
        return headers;
    }

    protected HttpHeaders getManagerHeader(){
        User us = new User();
        us.setLogin("manager");
        us.setName("manager");
        us.setRole(UserRole.MANAGER);
        us.setPassword(passwordEncoder.encode("manager"));
        userRepository.save(us);


        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("manager");
        loginDTO.setPassword("manager");
        HttpEntity<LoginDTO> tHttpEntity = new HttpEntity<>(loginDTO,new HttpHeaders());
        ResponseEntity<String> exchange = testRestTemplate.exchange("/login", HttpMethod.POST, tHttpEntity, String.class);
        String header = exchange.getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of("Bearer " + header));
        return headers;
    }


    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PostgresqlTestContainer.applyForLiquibase(applicationContext, "public");
        }

    }
}
