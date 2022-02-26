package ru.rodionov.spring.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.UserRole;
@Accessors(chain = true)
@Data
public class UserDTO {

    private Long id;
    private String login;
    private String password;
    private String name;
    private UserRole role;
//    private Long creatorId;
}
