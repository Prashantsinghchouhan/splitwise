package com.example.splitwise.split;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;

import java.util.List;

public interface SplitStrategy {
    List<ExpenseSplit> calculateSplit(Expense expense, AddExpenseRequest request);
}
