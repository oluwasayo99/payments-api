package com.oluwasayo.transactions.mapper;

import com.oluwasayo.guard.dto.IngestionRequestDto;
import com.oluwasayo.transactions.dto.TransactionStatusResponseDTO;
import com.oluwasayo.transactions.model.TransactionModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public TransactionStatusResponseDTO transactionResponse(TransactionModel transaction) {
        if (transaction == null) return null;
        TransactionStatusResponseDTO transactionDTO = new TransactionStatusResponseDTO();
        transactionDTO.setStatus(transaction.getStatus());
        return transactionDTO;
    }

    public void updateStatus(TransactionStatusResponseDTO dto, TransactionModel entity) {
        if (dto != null && dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
            entity.setUpdatedAt(java.time.LocalDateTime.now()); // update timestamp programmatically
        }
    }

    public TransactionModel ingestionToTransaction(IngestionRequestDto dto, String status) {
        TransactionModel transaction = new TransactionModel();
        transaction.setStatus(status);
        transaction.setAmount(dto.getAmount());
        transaction.setCurrency(dto.getCurrency());
        transaction.setSenderId(dto.getSenderId());
        transaction.setReceiverId(dto.getMerchantId());
        LocalDateTime now = java.time.LocalDateTime.now();
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);
        return transaction;
    }

}






