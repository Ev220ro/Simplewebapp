package ru.rodionov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.spring.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
