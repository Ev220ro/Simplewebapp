package ru.rodionov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.spring.model.Client;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByUser_Login(String login);
    List<Client> findAllByUser_CreatorId(Long id);

    Optional<Client> findByIdAndUser_Login(Long id, String login);

    void deleteByIdAndUser_Login(Long id, String login);
}
