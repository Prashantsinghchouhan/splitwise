package com.example.splitwise.service;

import com.example.splitwise.api.mapper.ExpenseResponseMapper;
import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.api.model.responsedto.ExpenseResponse;
import com.example.splitwise.constants.RequestTypes;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.ExpenseSplitRepository;
import com.example.splitwise.service.helper.ExpenseServiceHelper;
import com.example.splitwise.service.helper.IdempotencyService;
import com.example.splitwise.split.SplitStrategy;
import com.example.splitwise.split.SplitStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseService{
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final LedgerService ledgerService;
    private final ExpenseServiceHelper serviceHelper;
    private final IdempotencyService idempotencyService;
    private final SplitStrategyFactory splitStrategyFactory;

    @Transactional
    public ExpenseResponse addExpense(String idempotencyKey, AddExpenseRequest request) {
        Optional<ExpenseResponse> cachedResponse = idempotencyService.validateIdempotency(idempotencyKey, RequestTypes.EXPENSE_CREATE);
        if(cachedResponse.isPresent()){
            return cachedResponse.get();
        }
        serviceHelper.validateAddExpenseRequest(request);
        Expense expense = createExpense(request);
        List<ExpenseSplit> expenseSplits = createExpenseSplit(expense, request);
        ledgerService.applyExpenseToLedger(expense, expenseSplits);
        idempotencyService.markComplete(idempotencyKey, RequestTypes.EXPENSE_CREATE, expense.getId());
        return ExpenseResponseMapper.from(expense, expenseSplits);
    }

    private Expense createExpense(AddExpenseRequest request) {
        Expense expense =  Expense.builder()
                .groupId(request.getGroupId())
                .totalAmount(request.getAmount())
                .paidByUserId(request.getPaidByUserId())
                .description(request.getDescription())
                .splitType(request.getSplitType())
                .build();
        return expenseRepository.save(expense);
    }

    private List<ExpenseSplit> createExpenseSplit(Expense expense, AddExpenseRequest request) {
        SplitStrategy strategy = splitStrategyFactory.getStrategy(request.getSplitType());
        List<ExpenseSplit> expenseSplits = strategy.calculateSplit(expense, request);
        return expenseSplitRepository.saveAll(expenseSplits);
    }

}
