package com.example.splitwise.repository;

import com.example.splitwise.entity.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IdempotencyRepository extends JpaRepository<IdempotencyKey, String> {
    Optional<IdempotencyKey> findByIdempotencyKeyAndRequestType(String idempotencyKey, String requestType);
}
