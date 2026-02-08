package com.example.splitwise.split.impl;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.split.SplitStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplit(Expense expense, AddExpenseRequest request) {
        BigDecimal equalExpense = expense.getTotalAmount().divide(BigDecimal.valueOf(request.getParticipants().size()), 2, RoundingMode.HALF_UP);
        List<ExpenseSplit> expenseSplits = new ArrayList<>();
        for(String participant: request.getParticipants()){
            ExpenseSplit expenseSplit = ExpenseSplit.builder()
                    .expenseId(expense.getId())
                    .userId(participant)
                    .amount(equalExpense)
                    .build();
            expenseSplits.add(expenseSplit);
        }
        return expenseSplits;
    }
}
