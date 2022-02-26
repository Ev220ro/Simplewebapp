package ru.rodionov.spring.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.ClientStatus;
import ru.rodionov.spring.repository.ClientRepository;

import javax.persistence.*;
@Accessors(chain = true)
@Data
@Entity
@Table(name = "CLIENTS")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String phone;
    @Column
    @Enumerated(value = EnumType.STRING)
    private ClientStatus status;
    @Column
    private Long userId;

}
