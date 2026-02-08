package com.example.splitwise.split.impl;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.exception.validation.InvalidSplitException;
import com.example.splitwise.split.SplitStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ExactSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplit(Expense expense, AddExpenseRequest request) {
        Map<String, BigDecimal> exactAmounts = request.getSplitValues();
        BigDecimal totalSum = exactAmounts.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        if(totalSum.compareTo(expense.getTotalAmount()) != 0){
            throw InvalidSplitException.sumMismatch();
        }

        List<ExpenseSplit> expenseSplits = new ArrayList<>();
        for(String participant: request.getParticipants()){
            ExpenseSplit expenseSplit = ExpenseSplit.builder()
                    .expenseId(expense.getId())
                    .userId(participant)
                    .amount(request.getSplitValues().get(participant))
                    .build();
            expenseSplits.add(expenseSplit);
        }
        return expenseSplits;
    }
}
