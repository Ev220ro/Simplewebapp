package ru.rodionov.spring.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.rodionov.spring.enums.TransactionType;
@Accessors(chain = true)
@Data
public class TransactionDTO {

    private Long transactionId;

    private String dateTimeTransaction;

    private double sum;

    private TransactionType transactionType;

    private Long clientId;
}
