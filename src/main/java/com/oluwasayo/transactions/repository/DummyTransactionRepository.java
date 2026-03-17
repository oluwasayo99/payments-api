package com.oluwasayo.transactions.repository;

import com.oluwasayo.transactions.model.TransactionModel;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class DummyTransactionRepository {

    private final List<TransactionModel> dummyTransactions = List.of(
            new TransactionModel(1L, 10000L, "user-63726", "user-1234", "PROCESSING"),
            new TransactionModel(2L, 2000L, "user-234", "user-2938", "COMPLETED")
    );


    public Optional<TransactionModel> getTransactionById(Long id) {
        return dummyTransactions.stream().filter(x -> x.getId().equals(id)).findFirst();
    }


}
