package com.example.splitwise.api.controller;

import com.example.splitwise.api.model.requestdto.users.UserPatchRequest;
import com.example.splitwise.api.model.requestdto.users.UserPutRequest;
import com.example.splitwise.api.model.requestdto.users.UserRequest;
import com.example.splitwise.api.model.responsedto.UserResponse;
import com.example.splitwise.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest userRequest
    ) {
        UserResponse response = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable @NotBlank String userId
    ) {
        UserResponse response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @NotBlank String userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> putUser(
            @Valid @RequestBody UserPutRequest userRequest,
            @PathVariable @NotBlank String userId
    ) {
        UserResponse response = userService.putUser(userRequest, userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @Valid @RequestBody UserPatchRequest userRequest,
            @PathVariable @NotBlank String userId
    ) {
        UserResponse response = userService.updateUser(userRequest, userId);
        return ResponseEntity.ok(response);
    }
}