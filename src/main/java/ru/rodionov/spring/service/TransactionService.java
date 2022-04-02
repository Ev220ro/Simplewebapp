package ru.rodionov.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.rodionov.spring.DTO.TransactionDTO;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.exceptions.TransactionNotFoundException;
import ru.rodionov.spring.exceptions.UserNotFoundException;
import ru.rodionov.spring.model.Client;
import ru.rodionov.spring.model.Transaction;
import ru.rodionov.spring.repository.ClientRepository;
import ru.rodionov.spring.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
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

    public TransactionDTO create(TransactionDTO transactionDTO, Long id) {
        Client client = clientRepository.getById(id);
        Transaction transaction = new Transaction().setDateTime(transactionDTO.getDateTimeTransaction())
                   .setType(transactionDTO.getTransactionType())
                   .setAmount(transactionDTO.getAmount())
                   .setClient(client);
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
                .map(transaction -> updateTransaction(newTransactionDTO, transaction))
                .orElseThrow(()->new TransactionNotFoundException("Not found transaction " + id));
    }

    public TransactionDTO updateTransaction(TransactionDTO newTransactionDTO, Transaction transaction){
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
