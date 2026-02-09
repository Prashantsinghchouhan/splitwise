package com.example.splitwise.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class IdempotencyConfig {
    @Value("${idempotency.expense.expiry-minutes}")
    private long expenseIdempotencyExpiryMinutes;
}
