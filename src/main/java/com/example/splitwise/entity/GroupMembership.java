package com.example.splitwise.entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Getter
@Setter
@SuperBuilder
public class GroupMembership extends BaseEntity {

    private String groupId;
    private String userId;
    private Instant joinedAt;
    private Instant leftAt;

    public boolean isActive() {
        return leftAt == null;
    }
}
