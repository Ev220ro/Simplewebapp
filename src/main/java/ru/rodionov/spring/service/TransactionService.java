package ru.rodionov.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodionov.spring.DTO.TransactionDTO;
import ru.rodionov.spring.exceptions.TransactionNotFoundException;
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
        return new TransactionDTO().setTransactionType(transaction.getType())
                .setDateTimeTransaction(transaction.getDateTime())
                .setClientId(transaction.getClient().getId())
                .setAmount(transaction.getAmount())
                .setTransactionId(transaction.getId());
    }

    public TransactionDTO create(TransactionDTO transactionDTO) {
           Transaction transaction = new Transaction().setDateTime(transactionDTO.getDateTimeTransaction())
                   .setType(transactionDTO.getTransactionType())
                   .setAmount(transactionDTO.getAmount());
          // найти клиента по client_id и положить сюда        .setClient(transactionDTO.getClient());
           transactionRepository.save(transaction);
           return transactionDTO.setTransactionId(transaction.getId());
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
        transaction.setType(transaction.getType());
        transaction.setAmount(transaction.getAmount());
        transaction.setDateTime(transaction.getDateTime());
        transactionRepository.save(transaction);
        return newTransactionDTO.setTransactionId(transaction.getId());
    }


    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
