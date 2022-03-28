package ru.rodionov.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodionov.spring.DTO.ClientDTO;
import ru.rodionov.spring.exceptions.ClientNotFoundException;
import ru.rodionov.spring.model.Client;
import ru.rodionov.spring.model.User;
import ru.rodionov.spring.repository.ClientRepository;
import ru.rodionov.spring.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public List<ClientDTO> getAll(String login) {
        return clientRepository.findAllByUser_Login(login).stream()
                .map(this::getClientDTO)
                .collect(Collectors.toList());
    }

    public List<ClientDTO> getAllMyWorkers(String login) { //get all clients to my workers
        Optional<User> user = userRepository.findByLogin(login);
        Long id = user.get().getId();
        return clientRepository.findAllByUser_CreatorId(id).stream()
                .map(this::getClientDTO)
                .collect(Collectors.toList());
    }

    private ClientDTO getClientDTO(Client client) {
        return new ClientDTO().setId(client.getId())
                .setName(client.getName())
                .setSurname(client.getSurname())
                .setPhone(client.getPhone())
                .setStatus(client.getStatus())
                .setDelays(client.getDelays())
                .setIncome(client.getIncome());
    }


    public ClientDTO create(ClientDTO clientDTO, String login) {
        User user = userRepository.findByLogin(login).get();
        Client client = new Client()
                .setUser(user)
                .setName(clientDTO.getName())
                .setStatus(clientDTO.getStatus())
                .setPhone(clientDTO.getPhone())
                .setSurname(clientDTO.getSurname())
                .setDelays(clientDTO.getDelays())
                .setIncome(clientDTO.getIncome());

        clientRepository.save(client);
        return clientDTO.setId(client.getId());
    }

    public ClientDTO getById(Long id, String login) {
        return clientRepository.findByIdAndUser_Login(id, login)
                .map(this::getClientDTO)
                .orElseThrow(() -> new ClientNotFoundException("Not found " + id));
    }

    public ClientDTO updateClient(ClientDTO newClient, Long id, String login) {
        return clientRepository.findByIdAndUser_Login(id, login)
                .map(client -> getClient(newClient, client))
                .orElseThrow(() -> new ClientNotFoundException("Not found " + id));
    }

    private ClientDTO getClient(ClientDTO newClient, Client client) {
        client.setName(newClient.getName());
        client.setSurname(newClient.getSurname());
        client.setPhone(newClient.getPhone());
        client.setStatus(newClient.getStatus());
        client.setDelays(newClient.getDelays());
        client.setIncome(newClient.getIncome());
        clientRepository.save(client);
        return newClient.setId(client.getId());
    }

    @Transactional
    public void deleteClient(Long id, String login) {
        clientRepository.deleteByIdAndUser_Login(id, login);
    } //может ли удалять свои записи WORKER или такая функциональность у Менеджера?
}
