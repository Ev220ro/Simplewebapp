package ru.rodionov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.spring.model.Action;
import ru.rodionov.spring.model.Client;

import java.util.List;
import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findAllByUser_Login(String login);
    Optional<Action> findByIdAndUser_Login(Long id, String login);
}
