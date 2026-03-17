package com.oluwasayo.transactions.controller;
import com.oluwasayo.transactions.dto.TransactionStatusResponseDTO;
//import com.oluwasayo.payment_status.repository.TransactionRepository;
import com.oluwasayo.transactions.model.TransactionModel;
import com.oluwasayo.transactions.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<TransactionStatusResponseDTO> getTransactionStatus(@PathVariable Long id) {
        System.out.println("Fetch transaction from the controller: " + id);
        TransactionStatusResponseDTO response = transactionService.getTransactionResponseById(id);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/transactions")
//    public List<TransactionModel> getAllTransactions() {
//        return transactionService.getAllTransactions();
//    }

    @GetMapping("/transactions")
    public Page<TransactionModel> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return transactionService.getAllTransactions(page, size);

    }

    @PostMapping(path = "/transactions")
    public TransactionModel create(@RequestBody TransactionModel transactionModel) {
        return transactionService.save(transactionModel);
    }

    @PutMapping(path = "/transactions")
    public TransactionModel update(@RequestBody TransactionModel transactionModel) {
        return transactionService.update(transactionModel);
    }
}
