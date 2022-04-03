package ru.rodionov.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.rodionov.spring.DTO.LoginDTO;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.enums.UserRole;
import ru.rodionov.spring.model.User;

import java.util.ArrayList;
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

    @Test
    public void getAllTest(){
        User us1 = new User();
        us1.setLogin("admin1");
        us1.setName("admin1");
        us1.setRole(UserRole.ADMIN);
        us1.setPassword(passwordEncoder.encode("admin1"));
        userRepository.save(us1);

        User us2 = new User();
        us2.setLogin("admin2");
        us2.setName("admin2");
        us2.setRole(UserRole.ADMIN);
        us2.setPassword(passwordEncoder.encode("admin2"));
        userRepository.save(us2);

        UserDTO userDTO = new UserDTO();
        HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO, getAdminHeader());
        ResponseEntity<String> response = testRestTemplate.exchange("/user", HttpMethod.GET, userDTOHttpEntity, String.class);
        // String??
        System.out.println("//////////////////" + response);
        Assertions.assertNotNull(response.getBody());

    }

    @Test
    public void oneTest(){
        User us1 = new User();
        us1.setLogin("admin1");
        us1.setName("admin1");
        us1.setRole(UserRole.ADMIN);
        us1.setPassword(passwordEncoder.encode("admin1"));
        userRepository.save(us1);

        UserDTO userDTO = new UserDTO();
        HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO, getAdminHeader());

        long id = 1L;
        ResponseEntity<UserDTO> response = testRestTemplate.exchange("/user/{id}", HttpMethod.GET, userDTOHttpEntity, UserDTO.class, id);
        System.out.println("//////////////////" + response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(id, response.getBody().getId());

    }

}
