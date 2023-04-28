package com.logical.auth.model;


import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    @NotNull
    @NotBlank(message="firstName should not be blank ")
    private String firstName;
    @NotNull
    @NotBlank(message="lastName should not be blank ")
    private String lastName;
    @Email
    @NotBlank(message="Email should not be blank ")
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
    private String email;
    @Size(max = 15)
    @NotBlank(message = "Password is mandatory")
    private String password;
}
