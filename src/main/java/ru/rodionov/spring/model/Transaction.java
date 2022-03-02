package ru.rodionov.spring.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String dateTime;
    @Column
    private BigDecimal  amount;
    @Column
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}


