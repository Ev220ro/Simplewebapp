package ru.rodionov.spring.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.rodionov.spring.DTO.ClientDTO;
import ru.rodionov.spring.service.ClientService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/client")
@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDTO> getAll(Authentication auth) {
        return clientService.getAll(auth.getName());
    }
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @GetMapping("/getAllForManager")
    public List<ClientDTO> getAllToMyWorkers(Authentication auth){
        return clientService.getAllMyWorkers(auth.getName());
    }

    @PostMapping
    public ClientDTO create(@RequestBody ClientDTO clientDTO, Authentication auth) {
        return clientService.create(clientDTO, auth.getName());
    }

    @GetMapping("{id}")
    public ClientDTO one(@PathVariable Long id, Authentication auth) {
        return clientService.getById(id, auth.getName());
    }

    @PutMapping("{id}")
    public ClientDTO replaceClient(@RequestBody ClientDTO newClient, @PathVariable Long id, Authentication auth) {
        return clientService.updateClient(newClient, id, auth.getName());
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public void deleteClient(@PathVariable Long id, Authentication auth) {
        clientService.deleteClient(id, auth.getName());
    }
}
