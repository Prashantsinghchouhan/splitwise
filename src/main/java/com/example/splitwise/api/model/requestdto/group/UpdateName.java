package com.example.splitwise.api.model.requestdto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UpdateName {

    @NotBlank(message = "groupId is required")
    private String groupId;

    @NotBlank(message = "name is required")
    private String name;
}