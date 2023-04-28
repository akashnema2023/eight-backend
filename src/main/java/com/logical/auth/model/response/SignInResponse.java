package com.logical.auth.model.response;
import com.logical.auth.entity.UserData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class SignInResponse {
    boolean result;
    UserData data;
}
