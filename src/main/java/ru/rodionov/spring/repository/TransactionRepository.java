package ru.rodionov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import ru.rodionov.spring.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
