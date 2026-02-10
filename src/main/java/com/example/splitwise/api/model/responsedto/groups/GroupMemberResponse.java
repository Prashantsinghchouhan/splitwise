package com.example.splitwise.api.model.responsedto.groups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class GroupMemberResponse {
    private String groupId;
    private String userId;
    private boolean admin;
    private Instant joinedAt;
}