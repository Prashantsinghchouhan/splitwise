package com.example.splitwise.api.model.requestdto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class GroupRequest {

    @NotBlank(message = "group name is required")
    private String name;

    @NotBlank(message = "createdBy is required")
    private String createdBy;

    private String description;
}