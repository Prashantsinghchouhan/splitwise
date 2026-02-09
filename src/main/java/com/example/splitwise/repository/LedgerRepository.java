package com.example.splitwise.repository;

import com.example.splitwise.entity.Ledger;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LedgerRepository extends JpaRepository<Ledger, String> {
    Optional<Ledger> findByGroupIdAndUserId(String groupId, String userId);
}
