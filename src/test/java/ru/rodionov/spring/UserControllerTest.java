package ru.rodionov.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import ru.rodionov.spring.DTO.LoginDTO;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.enums.UserRole;
import ru.rodionov.spring.model.User;

import java.util.List;
import java.util.Optional;

public class UserControllerTest extends IntegrationTest{


    @Test
    public void createTest(){

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
        ResponseEntity<UserDTO[]> response = testRestTemplate.exchange("/user", HttpMethod.GET, userDTOHttpEntity, UserDTO[].class);
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

        long id = us1.getId();
        ResponseEntity<UserDTO> response = testRestTemplate.exchange("/user/{id}", HttpMethod.GET, userDTOHttpEntity, UserDTO.class, id);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(id, response.getBody().getId());

    }


    @Test
    public void createWorkerTest(){
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("worker");
        userDTO.setName("worker");
        userDTO.setLogin("worker");
        userDTO.setRole(UserRole.WORKER);

        HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO, getManagerHeader());

        ResponseEntity<UserDTO> response = testRestTemplate.exchange("/user/createWorker", HttpMethod.POST, userDTOHttpEntity, UserDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getId());
        User saved = userRepository.getById(response.getBody().getId());
        Assertions.assertNotNull(saved);
    }

    @Test
    public void getAllMyTest(){
        User us1 = new User();
        us1.setLogin("worker1");
        us1.setName("worker1");
        us1.setRole(UserRole.WORKER);
        us1.setPassword(passwordEncoder.encode("worker1"));
        userRepository.save(us1);

        User us2 = new User();
        us2.setLogin("worker2");
        us2.setName("worker2");
        us2.setRole(UserRole.WORKER);
        us2.setPassword(passwordEncoder.encode("worker2"));
        userRepository.save(us2);

        UserDTO userDTO = new UserDTO();
        HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO, getManagerHeader());
        ResponseEntity<UserDTO[]> response = testRestTemplate.exchange("/user/getAllMy", HttpMethod.GET, userDTOHttpEntity, UserDTO[].class);
        Assertions.assertNotNull(response.getBody());

    }

    @Test
    public void oneOfMyTest(){
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

        UserDTO userDTO = userService.getUserDTO(us);
        HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO, headers);

        User us1 = new User();
        us1.setLogin("worker1");
        us1.setName("worker1");
        us1.setRole(UserRole.WORKER);
        us1.setPassword(passwordEncoder.encode("worker1"));
        us1.setCreatorId(userDTO.getId());
        userRepository.save(us1);

        long id = us1.getId();
        ResponseEntity<UserDTO> response = testRestTemplate.exchange("/user/getById/{id}", HttpMethod.GET, userDTOHttpEntity, UserDTO.class, id);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(id, response.getBody().getId());

    }


}
