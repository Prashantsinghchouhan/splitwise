package com.example.splitwise.service;

import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.entity.Ledger;
import com.example.splitwise.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    public void applyExpense(Expense expense, List<ExpenseSplit> splits) {
        String groupId = expense.getGroupId();
        String payerId = expense.getPaidByUserId();
        for (ExpenseSplit split : splits) {
            BigDecimal amount = split.getAmount();
            String userId = split.getUserId();
            //Participant owes money
            updateBalance(groupId, userId, amount.negate());
            //Payer should receive money
            updateBalance(groupId, payerId, amount);
        }
    }

    private void updateBalance(String groupId, String userId, BigDecimal delta) {
        Ledger ledger = ledgerRepository.findByGroupIdAndUserId(groupId, userId).orElseGet(() ->
                Ledger.builder()
                        .balance(BigDecimal.ZERO)
                        .groupId(groupId)
                        .userId(userId)
                        .build());
        ledger.setBalance(ledger.getBalance().add(delta));
        ledgerRepository.save(ledger);
    }
}
