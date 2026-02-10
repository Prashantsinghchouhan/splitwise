package com.example.splitwise.api.model.responsedto.groups;

import com.example.splitwise.entity.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class GroupResponse {
    private String id;
    private String name;
    private String description;
    private String createdBy;
    private Instant createdAt;

    public static GroupResponse from(Group group){
        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .createdBy(group.getCreatedByUserId())
                .createdAt(group.getCreatedAt())
                .build();
    }
}
