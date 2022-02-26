package ru.rodionov.spring.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<ClientDTO> getAll() {
        return clientService.getAll();
    }

    @PostMapping
    public ClientDTO create(@RequestBody ClientDTO clientDTO) {
        return clientService.create(clientDTO);
    }

    @GetMapping("{id}")
    public ClientDTO one(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @PutMapping("{id}")
    public ClientDTO replaceClient(@RequestBody ClientDTO newClient, @PathVariable Long id) {
        return clientService.updateClient(newClient, id);
    }

    @DeleteMapping("{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
