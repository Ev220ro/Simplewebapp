package ru.rodionov.spring.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import ru.rodionov.spring.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;
@Accessors(chain = true)
@Data
@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column
    private String dateTimeTransaction;
    @Column
    private double sum;
    @Column
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
    @Column
    private Long clientId;
}


