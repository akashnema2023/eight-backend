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
public class UpdateUserRequest {
    @NotNull
    @NotBlank(message="firstName should not be blank ")
    private String firstName;
    @NotNull
    @NotBlank(message="lastName should not be blank ")
    private String lastName;
//    @Email
//    @NotBlank(message="Email should not be blank ")
//    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
//    private String email;

}
