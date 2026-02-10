package com.example.splitwise.service;

import com.example.splitwise.api.mapper.groups.GroupMemberResponseMapper;
import com.example.splitwise.api.model.requestdto.group.AddMemberRequest;
import com.example.splitwise.api.model.responsedto.groups.GroupMemberResponse;
import com.example.splitwise.entity.GroupMembership;
import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.authorisation.ForbiddenException;
import com.example.splitwise.exception.conflict.ResourceExistException;
import com.example.splitwise.exception.notfound.UserNotFoundException;
import com.example.splitwise.repository.GroupMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMembershipService {
    private final GroupMembershipRepository groupMembershipRepository;

    @Transactional
    public GroupMemberResponse addMember(String groupId, String actorUserId, AddMemberRequest request) {
        GroupMembership adminMembership = groupMembershipRepository.findByGroupIdAndUserId(groupId, actorUserId)
                        .orElseThrow(() -> UserNotFoundException.userNotFound(actorUserId));

        if (!adminMembership.isActive()) {
            throw new ForbiddenException("Inactive member cannot add users", ErrorCodes.FORBIDDEN_ACTION);
        }
        if (!adminMembership.isAdmin()) {
            throw new ForbiddenException("User is not admin", ErrorCodes.FORBIDDEN_ACTION);
        }

        groupMembershipRepository.findByGroupIdAndUserId(groupId, request.getUserId())
                .ifPresent(existing -> {
                    if (existing.isActive()) {
                        throw new ResourceExistException("User already exists", ErrorCodes.USER_ALREADY_EXIST);
                    }
                });

        GroupMembership newMembership = GroupMembership.builder()
                .groupId(groupId)
                .userId(request.getUserId())
                .admin(false)
                .joinedAt(Instant.now())
                .leftAt(null)
                .build();
        return GroupMemberResponseMapper.from(groupMembershipRepository.save(newMembership));
    }

    @Transactional
    public void removeMember(String groupId, String actorUserId, String targetUserId) {
        GroupMembership actor = groupMembershipRepository
                .findByGroupIdAndUserId(groupId, actorUserId)
                .orElseThrow(() ->
                        UserNotFoundException.userNotFound(actorUserId)
                );
        if (!actor.isActive()) {
            throw new ForbiddenException("Inactive user cannot perform this action",
                    ErrorCodes.FORBIDDEN_ACTION
            );
        }

        if (!actor.isAdmin()) {
            throw new ForbiddenException("Only admin can remove members",
                    ErrorCodes.FORBIDDEN_ACTION
            );
        }

        GroupMembership target = groupMembershipRepository
                .findByGroupIdAndUserId(groupId, targetUserId)
                .orElseThrow(() ->
                        UserNotFoundException.userNotFound(targetUserId)
                );

        if (!target.isActive()) {
            throw new ForbiddenException("User already removed from group",
                    ErrorCodes.FORBIDDEN_ACTION
            );
        }

        if (target.isAdmin()) {
            throw new ForbiddenException("Admin cannot be removed from group",
                    ErrorCodes.FORBIDDEN_ACTION
            );
        }
        target.setLeftAt(Instant.now());
        groupMembershipRepository.save(target);
    }

    @Transactional(readOnly = true)
    public List<GroupMemberResponse> listMembers(String groupId, String actorUserId) {
        GroupMembership actor = groupMembershipRepository
                .findByGroupIdAndUserId(groupId, actorUserId)
                .orElseThrow(() ->
                        new ForbiddenException(
                                "User is not part of this group",
                                ErrorCodes.FORBIDDEN_ACTION
                        )
                );
        if (!actor.isActive()) {
            throw new ForbiddenException(
                    "Inactive member cannot view group",
                    ErrorCodes.FORBIDDEN_ACTION
            );
        }
        return groupMembershipRepository
                .findByGroupIdAndLeftAtIsNull(groupId)
                .stream()
                .map(GroupMemberResponseMapper::from)
                .toList();
    }
}
