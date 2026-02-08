package com.example.splitwise.entity;

import com.example.splitwise.enums.SplitType;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseEntity {
    private String groupId;
    private String paidByUserId;
    private BigDecimal totalAmount;
    private String description;
    private SplitType splitType;
}
