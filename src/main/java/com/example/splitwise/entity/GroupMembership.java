package com.example.splitwise.entity;


import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMembership extends BaseEntity {

    private String groupId;
    private String userId;
    private Instant joinedAt;
    private Instant leftAt;

    public boolean isActive() {
        return leftAt == null;
    }
}
