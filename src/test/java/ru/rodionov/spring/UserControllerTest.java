package ru.rodionov.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.rodionov.spring.DTO.LoginDTO;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.enums.UserRole;
import ru.rodionov.spring.model.User;

import java.util.List;

public class UserControllerTest extends IntegrationTest{



    @Test
    public void create(){


        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("manager");
        userDTO.setName("manager");
        userDTO.setLogin("manager");
        userDTO.setRole(UserRole.MANAGER);



        HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO, getAdminHeader());

        ResponseEntity<UserDTO> response = testRestTemplate.exchange("/user", HttpMethod.POST, userDTOHttpEntity, UserDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getId());
        User saved = userRepository.getById(response.getBody().getId());
        Assertions.assertNotNull(saved);
    }

}
