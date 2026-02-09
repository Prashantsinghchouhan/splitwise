package com.example.splitwise.api.model.requestdto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDescription {

    @NotBlank(message = "groupId is required")
    private String groupId;
    @NotBlank(message = "description cannot be empty")
    private String description;
}