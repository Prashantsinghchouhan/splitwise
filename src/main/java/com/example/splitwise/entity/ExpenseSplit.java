package com.example.splitwise.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
public class ExpenseSplit extends BaseEntity {
    private String expenseId;
    private String userId;
    private BigDecimal amount;
}
