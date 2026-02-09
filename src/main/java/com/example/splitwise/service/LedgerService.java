package com.example.splitwise.service;

import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.entity.Ledger;
import com.example.splitwise.exception.conflict.ConcurrentUpdateException;
import com.example.splitwise.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    public void applyExpenseToLedger(Expense expense, List<ExpenseSplit> splits) {
        try {
            applyExpenseInternal(expense, splits);
        } catch (OptimisticLockingFailureException ex) {

            log.warn(
                    "Ledger optimistic lock conflict | expenseId={} groupId={}",
                    expense.getId(),
                    expense.getGroupId()
            );

            throw ConcurrentUpdateException.optimisticLockException();
        }
    }

    private void applyExpenseInternal(Expense expense, List<ExpenseSplit> splits) {
        String groupId = expense.getGroupId();
        String payerId = expense.getPaidByUserId();

        BigDecimal payerCredit = BigDecimal.ZERO;

        for (ExpenseSplit split : splits) {
            String userId = split.getUserId();
            BigDecimal amount = split.getAmount();

            if (!payerId.equals(userId)) {
                payerCredit = payerCredit.add(amount);
                updateBalance(groupId, userId, amount.negate());
            }
        }

        updateBalance(groupId, payerId, payerCredit);
    }

    @Transactional
    public void updateBalance(String groupId, String userId, BigDecimal delta) {
        Ledger ledger = ledgerRepository
                .findByGroupIdAndUserId(groupId, userId)
                .orElseGet(() ->
                        Ledger.builder()
                                .groupId(groupId)
                                .userId(userId)
                                .balance(BigDecimal.ZERO)
                                .build()
                );

        ledger.setBalance(ledger.getBalance().add(delta));
        ledgerRepository.save(ledger);
    }
}