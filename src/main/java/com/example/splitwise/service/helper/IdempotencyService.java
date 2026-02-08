package com.example.splitwise.service.helper;

import com.example.splitwise.api.mapper.ExpenseResponseMapper;
import com.example.splitwise.api.model.responsedto.ExpenseResponse;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.entity.IdempotencyKey;
import com.example.splitwise.exception.conflict.DuplicateRequestException;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.ExpenseSplitRepository;
import com.example.splitwise.repository.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdempotencyService {
    private final IdempotencyRepository idempotencyRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;

    @Transactional
    public Optional<ExpenseResponse> validateIdempotency(String key, String requestType){
        Optional<IdempotencyKey> existing = idempotencyRepository.findByIdempotencyKeyAndRequestType(key, requestType);
        if(existing.isPresent()){
            IdempotencyKey idempotencyKey = existing.get();
            if(idempotencyKey.getStatus() == IdempotencyKey.Status.COMPLETED){
                Expense expense = expenseRepository.findById(idempotencyKey.getResourceId())
                        .orElseThrow(()-> new IllegalStateException("Idempotency key not found in the database"));
                List<ExpenseSplit> expenseSplits = expenseSplitRepository.findByExpenseId(expense.getId());
                return Optional.ofNullable(ExpenseResponseMapper.from(expense, expenseSplits));
            }
            if(idempotencyKey.getStatus() == IdempotencyKey.Status.IN_PROGRESS){
                throw DuplicateRequestException.inProgress();
            }
        }
        //Idempotency key does not exist !!
        //Saving a new key !!
        try{
            idempotencyRepository.save(
                    IdempotencyKey.builder()
                            .idempotencyKey(key)
                            .requestType(requestType)
                            .status(IdempotencyKey.Status.IN_PROGRESS)
                            .build());
        } catch (DataIntegrityViolationException ex){
            throw DuplicateRequestException.inProgress();
        }
        return Optional.empty();
    }

    public void markComplete(String idempotencyKey, String requestType, String resourceId) {
        IdempotencyKey key = idempotencyRepository.findByIdempotencyKeyAndRequestType(idempotencyKey, requestType)
                .orElseThrow(()-> new IllegalStateException("Idempotency key not found while completing"));
        key.setResourceId(resourceId);
        key.setRequestType(requestType);
        idempotencyRepository.save(key);
    }
}