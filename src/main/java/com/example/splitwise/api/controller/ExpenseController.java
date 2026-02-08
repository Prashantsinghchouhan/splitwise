package com.example.splitwise.api.controller;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Void> addExpense(@RequestBody AddExpenseRequest addExpenseRequest){
        expenseService.addExpense(addExpenseRequest);
        return ResponseEntity.ok().build();
    }
}
