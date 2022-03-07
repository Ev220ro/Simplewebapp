package ru.rodionov.spring.model;

import ru.rodionov.spring.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "actions")
public class Action {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private LocalDateTime dateTime;
    @Column
    private AccessType type;
    @Column
    private String details;
    @Column
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
