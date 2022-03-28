package ru.rodionov.spring.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.ActionType;


import javax.persistence.*;
import java.time.LocalDateTime;
@Accessors(chain = true)
@Data
@Entity
@Table(name = "actions")
public class Action {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column
    @Enumerated(value = EnumType.STRING)
    private ActionType type;
    @Column
    private String details; //description
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
