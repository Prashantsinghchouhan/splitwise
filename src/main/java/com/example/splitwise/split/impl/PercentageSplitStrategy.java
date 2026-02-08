package com.example.splitwise.split.impl;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.exception.validation.InvalidSplitException;
import com.example.splitwise.split.SplitStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PercentageSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplit(Expense expense, AddExpenseRequest request) {
        Map<String, BigDecimal> percentages = request.getSplitValues();
        BigDecimal totalPercent = percentages.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        if(totalPercent.compareTo(BigDecimal.valueOf(100)) != 0){
            throw InvalidSplitException.percentMismatch();
        }

        List<ExpenseSplit> expenseSplits = new ArrayList<>();
        for(String participant: request.getParticipants()){
            BigDecimal percent = percentages.get(participant);
            BigDecimal amount = expense.getTotalAmount().multiply(percent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            ExpenseSplit expenseSplit = ExpenseSplit.builder()
                    .expenseId(expense.getId())
                    .userId(participant)
                    .amount(amount)
                    .build();

            expenseSplits.add(expenseSplit);
        }
        return expenseSplits;
    }
}
