package com.example.splitwise.service.helper;

import com.example.splitwise.api.mapper.ExpenseResponseMapper;
import com.example.splitwise.api.model.responsedto.ExpenseResponse;
import com.example.splitwise.config.IdempotencyConfig;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseSplit;
import com.example.splitwise.entity.IdempotencyKey;
import com.example.splitwise.exception.conflict.DuplicateRequestException;
import com.example.splitwise.exception.conflict.IdempotencyRequestException;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.ExpenseSplitRepository;
import com.example.splitwise.repository.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyConfig idempotencyConfig;
    private final IdempotencyRepository idempotencyRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;

    @Transactional
    public Optional<ExpenseResponse> validateIdempotency(
            String key,
            String requestType
    ) {
        Optional<IdempotencyKey> existing =
                idempotencyRepository.findByIdempotencyKeyAndRequestType(key, requestType);

        if (existing.isPresent()) {
            IdempotencyKey record = existing.get();

            log.info(
                    "Idempotency key found | key={} type={} status={}",
                    key,
                    requestType,
                    record.getStatus()
            );

            if (isExpired(record)) {
                log.warn(
                        "Idempotency key expired | key={} type={}",
                        key,
                        requestType
                );
                throw IdempotencyRequestException.expiredIdempotencyKey();
            }

            switch (record.getStatus()) {

                case COMPLETED -> {
                    log.info(
                            "Idempotent replay | key={} type={} resourceId={}",
                            key,
                            requestType,
                            record.getResourceId()
                    );

                    Expense expense = expenseRepository.findById(record.getResourceId())
                            .orElseThrow(() ->
                                    new IllegalStateException("Expense missing for idempotency key"));

                    List<ExpenseSplit> splits =
                            expenseSplitRepository.findByExpenseId(expense.getId());

                    return Optional.of(
                            ExpenseResponseMapper.from(expense, splits)
                    );
                }

                case IN_PROGRESS -> {
                    log.warn(
                            "Duplicate request in progress | key={} type={}",
                            key,
                            requestType
                    );
                    throw DuplicateRequestException.inProgress();
                }

                case FAILED -> {
                    log.warn(
                            "Retrying failed idempotent request | key={} type={}",
                            key,
                            requestType
                    );
                    markProgress(key, requestType);
                    return Optional.empty();
                }
            }
        }

        // 🔹 New idempotency key
        try {
            idempotencyRepository.save(
                    IdempotencyKey.builder()
                            .idempotencyKey(key)
                            .requestType(requestType)
                            .status(IdempotencyKey.Status.IN_PROGRESS)
                            .build()
            );

            log.info(
                    "New idempotency key created | key={} type={}",
                    key,
                    requestType
            );

        } catch (DataIntegrityViolationException ex) {
            log.warn(
                    "Concurrent idempotency creation detected | key={} type={}",
                    key,
                    requestType
            );
            throw DuplicateRequestException.inProgress();
        }

        return Optional.empty();
    }

    private boolean isExpired(IdempotencyKey key) {
        return key.getCreatedAt()
                .plus(Duration.ofMinutes(
                        idempotencyConfig.getExpenseIdempotencyExpiryMinutes()))
                .isBefore(Instant.now());
    }

    public void markComplete(String key, String requestType, String resourceId) {
        IdempotencyKey record =
                idempotencyRepository.findByIdempotencyKeyAndRequestType(key, requestType)
                        .orElseThrow();

        record.setResourceId(resourceId);
        record.setStatus(IdempotencyKey.Status.COMPLETED);
        idempotencyRepository.save(record);

        log.info(
                "Idempotency completed | key={} type={} resourceId={}",
                key,
                requestType,
                resourceId
        );
    }

    public void markProgress(String key, String requestType) {
        IdempotencyKey record =
                idempotencyRepository.findByIdempotencyKeyAndRequestType(key, requestType)
                        .orElseThrow();

        record.setStatus(IdempotencyKey.Status.IN_PROGRESS);
        idempotencyRepository.save(record);

        log.info(
                "Idempotency reset to IN_PROGRESS | key={} type={}",
                key,
                requestType
        );
    }

    public void markFailed(String key, String requestType) {
        IdempotencyKey record =
                idempotencyRepository.findByIdempotencyKeyAndRequestType(key, requestType)
                        .orElseThrow();

        record.setStatus(IdempotencyKey.Status.FAILED);
        idempotencyRepository.save(record);

        log.error(
                "Idempotency marked FAILED | key={} type={}",
                key,
                requestType
        );
    }
}