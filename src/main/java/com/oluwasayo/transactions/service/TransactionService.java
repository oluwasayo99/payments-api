package com.oluwasayo.transactions.service;
import com.oluwasayo.transactions.dto.TransactionStatusResponseDTO;
import com.oluwasayo.transactions.mapper.TransactionMapper;
import com.oluwasayo.transactions.model.TransactionModel;
import com.oluwasayo.transactions.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionStatusResponseDTO getTransactionResponseById(Long id) {
        System.out.println("Fetch transaction from the service: " + id);
        TransactionModel entity = transactionRepository.getTransactionById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "transaction with id " + id + "not found"
                )
        );
        System.out.println("After service fetch transaction from the controller: " + entity);
        return transactionMapper.transactionResponse(entity);
    }

    public List<TransactionModel> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Page<TransactionModel> getAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(pageable);
    }

    public TransactionModel save(TransactionModel transactionModel) {
        return transactionRepository.save(transactionModel);
    }

    public TransactionModel update(TransactionModel transactionModel) {
        TransactionModel entity = transactionRepository.getTransactionById(transactionModel.getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "transaction with id " + transactionModel.getId() + "not found"
                )
        );

        entity.setStatus(transactionModel.getStatus());
        entity.setUpdatedAt(java.time.LocalDateTime.now());
        return transactionRepository.save(entity);

    }

}
