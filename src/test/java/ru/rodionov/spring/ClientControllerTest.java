package ru.rodionov.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.rodionov.spring.DTO.ClientDTO;
import ru.rodionov.spring.enums.ClientStatus;
import ru.rodionov.spring.model.Client;
import ru.rodionov.spring.model.User;
import ru.rodionov.spring.repository.ClientRepository;

import java.util.Optional;


public class ClientControllerTest extends IntegrationTest{
    @Autowired
    ClientRepository clientRepository;
    
    @Test
    public void create(){

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIncome(100L);
        clientDTO.setStatus(ClientStatus.AGREE);
        clientDTO.setDelays(1L);
        clientDTO.setPhone("0123");
        clientDTO.setName("Ivan");
        clientDTO.setSurname("Ivanov");

        HttpEntity<ClientDTO> clientDTOHttpEntity = new HttpEntity<>(clientDTO, getAdminHeader());

        ResponseEntity<ClientDTO> response = testRestTemplate.exchange("/client", HttpMethod.POST, clientDTOHttpEntity, ClientDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Optional<Client> expectedClient = clientRepository.findById(response.getBody().getId());
        Assertions.assertTrue(expectedClient.isPresent());

    }

}
