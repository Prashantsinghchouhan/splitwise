package com.example.splitwise.api.model.requestdto.users;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPatchRequest {

    private String firstName;
    private String lastName;
    private String middleName;

    @Email(message = "invalid email format")
    private String email;

    private String phoneNumber;
}