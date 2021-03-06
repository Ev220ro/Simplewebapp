package ru.rodionov.spring.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String login;
    @Column
    private String  password;
    @Column
    private String name;
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    @Column
    private Long creatorId;
    @OneToMany(mappedBy = "user")
    private List<Client> clientList = new ArrayList<>();

}
