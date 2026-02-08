package com.example.splitwise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "idempotency_keys",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"idempotencyKey", "requestType"})
        }
)
public class IdempotencyKey extends BaseEntity {

    @Column(nullable = false)
    private String idempotencyKey;

    @Column(nullable = false)
    private String requestType;

    @Column
    private String resourceId;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }
}