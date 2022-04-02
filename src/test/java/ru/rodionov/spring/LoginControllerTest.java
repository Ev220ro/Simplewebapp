package ru.rodionov.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.rodionov.spring.DTO.LoginDTO;
import ru.rodionov.spring.enums.UserRole;
import ru.rodionov.spring.model.User;

public class LoginControllerTest extends IntegrationTest{
    @Test
    public void loginTest() {
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

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("admin");
        loginDTO.setPassword("admin2");
        HttpEntity<LoginDTO> tHttpEntity = new HttpEntity<>(loginDTO,new HttpHeaders());
        ResponseEntity<String> exchange = testRestTemplate.exchange("/login", HttpMethod.POST, tHttpEntity, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exchange.getStatusCode());

    }
}
