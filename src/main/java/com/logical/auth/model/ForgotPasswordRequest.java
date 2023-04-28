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
public class ForgotPasswordRequest {
    @Email
    @NotBlank(message="Email should not be blank ")
    @NotNull(message="Email should not be null ! ")
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
    private String email;
}
