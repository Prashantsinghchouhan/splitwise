package com.example.splitwise.concurrency;

import com.example.splitwise.entity.Ledger;
import com.example.splitwise.repository.LedgerRepository;
import com.example.splitwise.service.LedgerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LedgerOptimisticLockTest {

    @Autowired
    LedgerRepository ledgerRepository;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    PlatformTransactionManager txManager;

    ExecutorService executor = Executors.newFixedThreadPool(2);

    @Test
    void shouldFailWithOptimisticLock() throws Exception {

        ledgerRepository.save(
                Ledger.builder()
                        .groupId("g1")
                        .userId("u1")
                        .balance(BigDecimal.ZERO)
                        .build()
        );

        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);

        Callable<Void> task = () -> {
            TransactionTemplate tx = new TransactionTemplate(txManager);
            tx.execute(status -> {
                ready.countDown();
                try { start.await(); } catch (InterruptedException e) {}
                ledgerService.updateBalanceWithLock("g1", "u1", BigDecimal.TEN);
                return null;
            });
            return null;
        };

        Future<Void> f1 = executor.submit(task);
        Future<Void> f2 = executor.submit(task);

        ready.await();
        start.countDown();

        int failures = 0;

        try { f1.get(); } catch (ExecutionException e) {
            if (e.getCause() instanceof OptimisticLockingFailureException) failures++;
        }

        try { f2.get(); } catch (ExecutionException e) {
            if (e.getCause() instanceof OptimisticLockingFailureException) failures++;
        }

        assertEquals(1, failures);
    }
}