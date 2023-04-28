package com.logical.auth.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {
    @Email
    @NotBlank(message="Email should not be blank ")
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
    private String email;
    @Size(max = 15)
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Size(max = 15)
    @NotBlank(message = "New Password is mandatory")
    private String newPassword;
}
