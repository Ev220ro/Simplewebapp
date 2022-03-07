package ru.rodionov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.spring.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {

}
