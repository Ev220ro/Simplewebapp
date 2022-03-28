package ru.rodionov.spring.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.ActionType;



import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class ActionDTO {

    private Long id;
    private LocalDateTime dateTime;
    private ActionType type;
    private String details;

}
