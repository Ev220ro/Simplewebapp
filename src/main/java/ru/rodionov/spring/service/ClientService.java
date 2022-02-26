package ru.rodionov.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodionov.spring.DTO.ClientDTO;
import ru.rodionov.spring.exceptions.ClientNotFoundException;
import ru.rodionov.spring.model.Client;
import ru.rodionov.spring.repository.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDTO> getAll() {
        return clientRepository.findAll().stream()
                .map(this::getClientDTO)
                .collect(Collectors.toList());
    }

    private ClientDTO getClientDTO(Client client) {
        return new ClientDTO().setClientId(client.getClientId())
                .setUserId(client.getUserId())
                .setName(client.getName())
                .setSurname(client.getSurname())
                .setPhone(client.getPhone())
                .setStatus(client.getStatus());
    }


    public ClientDTO create(ClientDTO clientDTO) {
        Client client = new Client().setUserId(clientDTO.getUserId())
                .setName(clientDTO.getName())
                .setStatus(clientDTO.getStatus())
                .setPhone(clientDTO.getPhone())
                .setSurname(clientDTO.getSurname());

        clientRepository.save(client);
        return clientDTO.setClientId(client.getClientId());
    }

    public ClientDTO getById(Long id) {
        return clientRepository.findById(id)
                .map(this::getClientDTO)
                .orElseThrow(() -> new ClientNotFoundException("Not found " + id));
    }

    public ClientDTO updateClient(ClientDTO newClient, Long id) {
        return clientRepository.findById(id)
                .map(client -> getClient(newClient, client))
                .orElseThrow(() -> new ClientNotFoundException("Not found " + id));
    }

    private ClientDTO getClient(ClientDTO newClient, Client client) {
        client.setName(newClient.getName());
        client.setSurname(newClient.getSurname());
        client.setPhone(newClient.getPhone());
        client.setStatus(newClient.getStatus());
        client.setUserId(newClient.getUserId());
        clientRepository.save(client);
        return newClient.setClientId(client.getClientId());
    }


    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
