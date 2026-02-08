package com.example.splitwise.api.mapper;

import com.example.splitwise.api.model.responsedto.ExpenseResponse;
import com.example.splitwise.api.model.responsedto.SplitResponse;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;

import java.util.List;
import java.util.stream.Collectors;

public final class ExpenseResponseMapper {
    private ExpenseResponseMapper() {}

    public static ExpenseResponse from(Expense expense, List<ExpenseSplit> expenseSplitList){
        List<SplitResponse> splitResponses = buildSplitResponse(expenseSplitList);
        return ExpenseResponse.builder()
                .expenseId(expense.getId())
                .groupId(expense.getGroupId())
                .paidByUserId(expense.getPaidByUserId())
                .splitType(expense.getSplitType())
                .splits(splitResponses)
                .build();
    }

    private static List<SplitResponse> buildSplitResponse(List<ExpenseSplit> expenseSplitList) {
        return expenseSplitList.stream().map(expenseSplit -> SplitResponse.builder()
                    .amount(expenseSplit.getAmount())
                    .userId(expenseSplit.getUserId())
                    .build())
                .collect(Collectors.toList());
    }
}
