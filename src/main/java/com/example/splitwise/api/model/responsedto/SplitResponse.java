package com.example.splitwise.api.model.responsedto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class SplitResponse {
    private String userId;
    private BigDecimal amount;

}
