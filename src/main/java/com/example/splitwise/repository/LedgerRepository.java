package com.example.splitwise.repository;

import com.example.splitwise.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LedgerRepository extends JpaRepository<Ledger, String> {
    Optional<Ledger> findByGroupIdAndUserId(String groupId, String userId);
}
