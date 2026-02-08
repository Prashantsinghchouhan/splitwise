package com.example.splitwise.repository;

import com.example.splitwise.entity.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<GroupMembership, String> {
    boolean existsByGroupIdAndUserId(String groupId, String userId);
}
