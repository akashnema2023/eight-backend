package com.logical.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForgotPasswordMessage {
    boolean result;
    ForgotPasswordResponse data;
}
