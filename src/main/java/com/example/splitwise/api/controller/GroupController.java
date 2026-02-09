package com.example.splitwise.api.controller;

import com.example.splitwise.api.model.requestdto.group.GroupRequest;
import com.example.splitwise.api.model.requestdto.group.UpdateDescription;
import com.example.splitwise.api.model.requestdto.group.UpdateName;
import com.example.splitwise.api.model.responsedto.GroupResponse;
import com.example.splitwise.service.GroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final GroupService groupService;

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
}