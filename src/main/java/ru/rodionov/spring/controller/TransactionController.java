package ru.rodionov.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rodionov.spring.DTO.TransactionDTO;
import ru.rodionov.spring.service.TransactionService;

import java.util.List;

@RequestMapping("/transaction")
@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionDTO> findAll() {
        return transactionService.getAll();
    }

    @PostMapping
    public TransactionDTO create(@RequestBody TransactionDTO transactionDTO, @PathVariable Long id) {
        return transactionService.create(transactionDTO, id);
    }

    @GetMapping("{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PutMapping("{id}")
    public TransactionDTO replaceTransaction(@RequestBody TransactionDTO newTransactionDTO, @PathVariable Long id) {
        return transactionService.update(newTransactionDTO, id);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }


}
