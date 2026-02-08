package com.example.splitwise.service;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.ExpenseSplitRepository;
import com.example.splitwise.service.helper.ExpenseServiceHelper;
import com.example.splitwise.split.SplitStrategy;
import com.example.splitwise.split.SplitStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService{
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final LedgerService ledgerService;
    private final ExpenseServiceHelper serviceHelper;
    private final SplitStrategyFactory splitStrategyFactory;

    @Transactional
    public void addExpense(AddExpenseRequest request) {
        //validate the request !!
        serviceHelper.validateAddExpenseRequest(request);
        //createExpense
        Expense expense = createExpense(request);
        expenseRepository.save(expense);
        //create expense split;
        List<ExpenseSplit> expenseSplits = createExpenseSplit(expense, request);
        expenseSplitRepository.saveAll(expenseSplits);
        //update ledger
        ledgerService.applyExpense(expense, expenseSplits);
    }

    private Expense createExpense(AddExpenseRequest request) {
        return Expense.builder()
                .groupId(request.getGroupId())
                .totalAmount(request.getAmount())
                .paidByUserId(request.getPaidByUserId())
                .description(request.getDescription())
                .build();
    }

    private List<ExpenseSplit> createExpenseSplit(Expense expense, AddExpenseRequest request) {
        SplitStrategy strategy = splitStrategyFactory.getStrategy(request.getSplitType());
        return strategy.calculateSplit(expense, request);
    }

}
