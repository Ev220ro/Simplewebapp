package ru.rodionov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.spring.model.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {

}
