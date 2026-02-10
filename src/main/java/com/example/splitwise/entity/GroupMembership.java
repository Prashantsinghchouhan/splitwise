package com.example.splitwise.entity;


import jakarta.persistence.*;
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
@Table(
        name = "group_memberships",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_group_user",
                        columnNames = {"group_id", "user_id"}
                )
        }
)
public class GroupMembership extends BaseEntity {
    @Column(name = "group_id", nullable = false)
    private String groupId;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(nullable = false)
    private boolean admin;
    private Instant joinedAt;
    private Instant leftAt;

    public boolean isActive() {
        return leftAt == null;
    }

    @PrePersist
    public void onJoin() {
        this.joinedAt = Instant.now();
    }
}
