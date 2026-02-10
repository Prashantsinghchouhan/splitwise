package com.example.splitwise.api.controller;

import com.example.splitwise.api.model.requestdto.group.AddMemberRequest;
import com.example.splitwise.api.model.requestdto.group.GroupRequest;
import com.example.splitwise.api.model.requestdto.group.UpdateDescription;
import com.example.splitwise.api.model.requestdto.group.UpdateName;
import com.example.splitwise.api.model.responsedto.groups.GroupMemberResponse;
import com.example.splitwise.api.model.responsedto.groups.GroupResponse;
import com.example.splitwise.auth.AuthContext;
import com.example.splitwise.entity.GroupMembership;
import com.example.splitwise.service.GroupMembershipService;
import com.example.splitwise.service.GroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final GroupService groupService;
    private final GroupMembershipService groupMembershipService;
    @Qualifier("headerAuthContext")
    private final AuthContext authContext;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(
            @Valid @RequestBody GroupRequest groupRequest
    ) {
        GroupResponse groupResponse = groupService.createGroup(groupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupResponse);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroup(
            @PathVariable @NotBlank String groupId
    ) {
        GroupResponse groupResponse = groupService.getGroup(groupId);
        return ResponseEntity.ok(groupResponse);
    }

    @PatchMapping("/description")
    public ResponseEntity<GroupResponse> updateDescription(
            @Valid @RequestBody UpdateDescription request
    ) {
        GroupResponse groupResponse = groupService.updateDescription(request);
        return ResponseEntity.ok(groupResponse);
    }

    @PatchMapping("/name")
    public ResponseEntity<GroupResponse> updateName(
            @Valid @RequestBody UpdateName request
    ) {
        GroupResponse groupResponse = groupService.updateName(request);
        return ResponseEntity.ok(groupResponse);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<GroupMemberResponse> addMember(@PathVariable String groupId, @RequestBody AddMemberRequest request) {
        String actorUserId = authContext.getCurrentUserId();
        GroupMemberResponse response = groupMembershipService.addMember(groupId, actorUserId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable String groupId, @PathVariable String userId) {
        String actorUserId = authContext.getCurrentUserId();
        groupMembershipService.removeMember(groupId, actorUserId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMemberResponse>> listMembers(
            @PathVariable String groupId
    ) {
        String actorUserId = authContext.getCurrentUserId();
        return ResponseEntity.ok(
                groupMembershipService.listMembers(groupId, actorUserId)
        );
    }
}