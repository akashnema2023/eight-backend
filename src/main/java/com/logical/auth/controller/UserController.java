package com.logical.auth.controller;
import com.logical.auth.model.*;
import com.logical.auth.model.response.MessageResponse;
import com.logical.auth.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/signUp")
    public ResponseEntity<?> userSignUp(@Valid @RequestBody SignUpRequest signUpRequest){
        try {
//            while(result.hasErrors()){
////                System.out.println(result.getAllErrors());
//                List<FieldError> list=new LinkedList<>();
//                FieldError firstName = result.getFieldError("firstName");
//                FieldError lastName = result.getFieldError("lastName");
//                list.add(firstName);
//                list.add(lastName);
//                System.out.println(firstName);
//                return new ResponseEntity<>(list,HttpStatus.NOT_ACCEPTABLE);
//            }
             return userService.signUp(signUpRequest);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> userSignIn(@Valid @RequestBody SignInRequest signInRequest){
        try {
            return userService.signIn(signInRequest);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getUserProfile")
    public ResponseEntity<?> getUserById(@RequestParam(name = "userId",required = true,defaultValue="0")Long userId){
        try {
              return userService.getUserById(userId);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/updateUserProfile")
    public ResponseEntity<?>updateUserProfile(@RequestParam(name="profileImg",required = false) MultipartFile profileImg, @RequestParam(name="backgroundImg",required = false) MultipartFile backgroundImg,@RequestPart(name = "userId",required =true)Long userId,@RequestPart(name = "firstName",required =false)String firstName,@RequestPart(name = "description",required =false)String description){
        //@RequestPart("updateUserRequest") UpdateUserRequest updateUserRequest, @RequestParam(name = "userId",required =true,defaultValue ="0")Long userId)throws IOException {
        try {
            return userService.updateUserData(profileImg,backgroundImg,userId,firstName,description);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    @PostMapping("/changePassword")

    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        try {
              return userService.updatePassword(updatePasswordRequest);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PostMapping("/sendOTP")
//    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
//        try {
//          //  if (!(forgotPasswordRequest.getEmail().equals("null"))) {
//                return userService.forgotPassword(forgotPasswordRequest);
////            }
////            else{
////                return new ResponseEntity<>(new MessageResponse("Please provide valid email !", "400"), HttpStatus.NOT_ACCEPTABLE);
////            }
//        } catch (Exception e) {
//            logger.info(" "+e);
//            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping("/sentOTP")
    public ResponseEntity<?> forgotPassword(@RequestParam(name = "emailId",required = true)String emailId) {
        //      try {
        //  if (!(forgotPasswordRequest.getEmail().equals("null"))) {
        return userService.forgotPassword(emailId);
//            }
//            else{
//                return new ResponseEntity<>(new MessageResponse("Please provide valid email !", "400"), HttpStatus.NOT_ACCEPTABLE);
//            }
//        } catch (Exception e) {
//            logger.info(" "+e);
//            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity<?>deleteUser(@RequestParam(name = "userId",required = true,defaultValue="0")Long userId){
        try {
            return userService.deleteUser(userId);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/signOut")
    public ResponseEntity<?> logOutUser(@RequestParam(name = "userId",required = true,defaultValue = "0")Long userId) {
            return userService.logOutUser(userId);
    }

    @GetMapping("/showUserImageByUserId")
    public String showUserImageByUserId(@RequestPart(name = "userId",required = true)long userId){
        if(userId>0) {
            return userService.showUserImageByUserId(userId);
        }else{
            return "Provide valid id!!";
        }
    }


    @PostMapping("/verifyOTP")
    public ResponseEntity<?> verifyOTP(@RequestPart(name = "otp", required = true) int otp) {
        return userService.verifyOtp(otp);
    }

}
