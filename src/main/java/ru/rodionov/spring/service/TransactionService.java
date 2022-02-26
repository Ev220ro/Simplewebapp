package ru.rodionov.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.rodionov.spring.DTO.TransactionDTO;
import ru.rodionov.spring.exceptions.TransactionNotFoundException;
import ru.rodionov.spring.model.Client;
import ru.rodionov.spring.model.Transaction;
import ru.rodionov.spring.repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll()
                .stream().map(this::getTransactionDTO)
                .collect(Collectors.toList());

    }
    public TransactionDTO getTransactionDTO(Transaction transaction){
        return new TransactionDTO().setTransactionType(transaction.getTransactionType())
                .setDateTimeTransaction(transaction.getDateTimeTransaction())
                .setClientId(transaction.getClientId())
                .setSum(transaction.getSum())
                .setTransactionId(transaction.getTransactionId());
    }

    public TransactionDTO create(TransactionDTO transactionDTO) {
           Transaction transaction = new Transaction().setDateTimeTransaction(transactionDTO.getDateTimeTransaction())
                   .setTransactionType(transactionDTO.getTransactionType())
                   .setSum(transactionDTO.getSum())
                   .setClientId(transactionDTO.getClientId());
           transactionRepository.save(transaction);
           return transactionDTO.setTransactionId(transaction.getTransactionId());
    }



    public TransactionDTO getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(this::getTransactionDTO)
                .orElseThrow(()-> new TransactionNotFoundException("Not found transaction " + id));
    }

    public TransactionDTO update(TransactionDTO newTransactionDTO, Long id) {
        return transactionRepository.findById(id)
                .map(this::getTransactionDTO)
                .orElseThrow(()->new TransactionNotFoundException("Not found transaction " + id));
    }

    public TransactionDTO getTransaction(TransactionDTO newTransactionDTO, Transaction transaction){
        transaction.setTransactionType(transaction.getTransactionType());
        transaction.setSum(transaction.getSum());
        transaction.setDateTimeTransaction(transaction.getDateTimeTransaction());
        transaction.setClientId(transaction.getClientId());
        transactionRepository.save(transaction);
        return newTransactionDTO.setTransactionId(transaction.getTransactionId());
    }


    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
