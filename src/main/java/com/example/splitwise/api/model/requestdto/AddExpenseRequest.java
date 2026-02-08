package com.example.splitwise.api.model.requestdto;

import com.example.splitwise.enums.SplitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class AddExpenseRequest {
    private String groupId;
    private String paidByUserId;
    private BigDecimal amount;
    private SplitType splitType;
    private List<String> participants; //user_id
    private Map<String, BigDecimal> splitValues; //used for EXACT / PERCENTAGE splits
    private String description;
}
