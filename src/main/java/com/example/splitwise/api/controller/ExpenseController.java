package com.example.splitwise.api.controller;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.api.model.responsedto.ExpenseResponse;
import com.example.splitwise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> addExpense(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody AddExpenseRequest addExpenseRequest){
        ExpenseResponse response = expenseService.addExpense(idempotencyKey, addExpenseRequest);
        return ResponseEntity.ok(response);
    }
}
