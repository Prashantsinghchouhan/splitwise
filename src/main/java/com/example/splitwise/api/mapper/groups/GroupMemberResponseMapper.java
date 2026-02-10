package com.example.splitwise.api.mapper.groups;

import com.example.splitwise.api.model.responsedto.groups.GroupMemberResponse;
import com.example.splitwise.entity.GroupMembership;

public final class GroupMemberResponseMapper {

    private GroupMemberResponseMapper() {}

    public static GroupMemberResponse from(GroupMembership membership) {
        return GroupMemberResponse.builder()
                .groupId(membership.getGroupId())
                .userId(membership.getUserId())
                .admin(membership.isAdmin())
                .joinedAt(membership.getJoinedAt())
                .build();
    }
}