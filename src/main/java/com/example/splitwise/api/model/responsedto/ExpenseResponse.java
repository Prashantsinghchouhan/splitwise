package com.example.splitwise.api.model.responsedto;

import com.example.splitwise.enums.SplitType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ExpenseResponse {
    private String expenseId;
    private String groupId;
    private String paidByUserId;
    private BigDecimal totalAmount;
    private SplitType splitType;
    private List<SplitResponse> splits;
}
