package com.example.splitwise.service;

import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.entity.Ledger;
import com.example.splitwise.exception.conflict.ConcurrentUpdateException;
import com.example.splitwise.repository.LedgerRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    public void applyExpenseToLedger(Expense expense, List<ExpenseSplit> splits) {
        try{
            applyExpenseInternal(expense, splits);
        } catch (OptimisticLockingFailureException e) {
            log.warn("Optimistic lock failure while updating ledger. groupId={}, payerId={}",
                    expense.getGroupId(),
                    expense.getPaidByUserId(),
                    e
            );
            throw ConcurrentUpdateException.optimisticLockException();
        }
    }

    public void applyExpenseInternal(Expense expense, List<ExpenseSplit> splits) {
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
