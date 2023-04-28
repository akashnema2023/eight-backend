package com.logical.auth.services;

import com.logical.auth.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    ResponseEntity<?> signUp(SignUpRequest signUpRequest);
    ResponseEntity<?> signIn(SignInRequest signInRequest);
    ResponseEntity<?> getUserById(Long userId);
   // ResponseEntity<?> updateUserData(MultipartFile profileImg, MultipartFile backgroundImg, UpdateUserRequest updateUserRequest, Long userId,String firstName,String description) throws IOException;

    ResponseEntity<?> updateUserData(MultipartFile profileImg, MultipartFile backgroundImg, Long userId, String firstName, String description) throws IOException;

    ResponseEntity<?> forgotPassword(String mail);
    ResponseEntity<?> updatePassword(UpdatePasswordRequest updatePasswordRequest);
    ResponseEntity<?>deleteUser(Long userId);
    ResponseEntity<?> logOutUser(Long userId);
}
