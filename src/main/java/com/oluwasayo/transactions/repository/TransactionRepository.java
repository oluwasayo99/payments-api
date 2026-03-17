package com.oluwasayo.transactions.repository;

import com.oluwasayo.transactions.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
     Optional<TransactionModel> getTransactionById(Long id);


}
