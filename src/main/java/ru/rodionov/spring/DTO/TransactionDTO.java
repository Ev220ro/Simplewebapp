package ru.rodionov.spring.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.TransactionType;

import java.math.BigDecimal;

@Accessors(chain = true)
@Data
public class TransactionDTO {

    private Long transactionId;


    private String dateTimeTransaction;

    private BigDecimal amount;

    private TransactionType transactionType;

    private Long clientId;
}
