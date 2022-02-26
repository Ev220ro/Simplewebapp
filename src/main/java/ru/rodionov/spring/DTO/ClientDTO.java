package ru.rodionov.spring.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.ClientStatus;
@Accessors(chain = true)
@Data
public class ClientDTO {

    private Long clientId;
    private String name;
    private String surname;
    private String phone;
    private ClientStatus status;
    private Long userId;



}
