package com.example.splitwise.repository;

import com.example.splitwise.entity.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, String> {
    Optional<GroupMembership> findByGroupIdAndUserId(String groupId, String id);
    List<GroupMembership> findByGroupIdAndLeftAtIsNull(String groupId);
}
