package ru.rodionov.spring.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LoginDTO {
    private String userName;
    private String password;
}
