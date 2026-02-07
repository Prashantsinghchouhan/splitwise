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
public class Ledger extends BaseEntity {
    private String groupId;
    private String userId;
    private BigDecimal balance;
}
