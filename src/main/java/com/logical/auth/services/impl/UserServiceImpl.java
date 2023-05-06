package com.logical.auth.services.impl;

import com.logical.auth.entity.VideoData;
import com.logical.auth.repository.VideoRepository;
import com.logical.auth.services.EmailService;
import com.logical.auth.services.StorageServices;
import com.logical.auth.entity.UserData;
import com.logical.auth.model.*;
import com.logical.auth.model.response.*;
import com.logical.auth.repository.UserRepository;
import com.logical.auth.security.jwt.JwtUtils;
import com.logical.auth.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
//    @Autowired
//    FileService fileService;

    @Autowired
    StorageServices fileService;
    @Value("${project.image}")
    String path;


    int verifyOtp = 0;
    String email = "";


    @Autowired
    EmailService emailService;
    Random rand = new Random();

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoService videoService;

    @Override
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail().toLowerCase())) {
            return new ResponseEntity<>(new MessageResponse(false, "Email is already in use!"), HttpStatus.IM_USED);
        }
        Random rand = new Random();
        UserData user = this.modelMapper.map(signUpRequest, UserData.class);
        user.setEmail(signUpRequest.getEmail().toLowerCase());
//        user.setProfileImgUrl("https://seven02.s3.ap-south-1.amazonaws.com/defaultimg.jpg");
//        user.setBackgroundImgUrl("https://seven02.s3.ap-south-1.amazonaws.com/backgorundimg.jpg");
        user.setTotalSubscriber(0);
        user.setTotalUploadedVideos(0);
        int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
        String uri = "https://www.youtube.com/@" + signUpRequest.getFirstName() + "" + signUpRequest.getLastName() + "" + resRandom;
        user.setChannelUri(uri);
        user.setActive(false);
        userRepository.save(user);
//        if (save != null) {
//            return new ResponseEntity<>(save, HttpStatus.NOT_ACCEPTABLE);
//        }
        return new ResponseEntity<>(new MessageResponse(true, "User registered successfully!"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> signIn(SignInRequest signInRequest) {
        if (userRepository.existsByEmail(signInRequest.getEmail().toLowerCase())) {
            UserData userData = this.userRepository.getByEmail(signInRequest.getEmail().toLowerCase());
            if (userData.getPassword().equals(signInRequest.getPassword())) {
                userData.setActive(true);
                return new ResponseEntity<>(new SignInResponse(true, userData), HttpStatus.OK);
                // return ResponseEntity.ok(new SignInResponse(true , userData));
                // return new ResponseEntity<>(new MessageResponse("User login successfully!", true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse(false, "Password is not matched !"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this email !"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getUserById(Long userId) {
        if (userId > 0) {
            if (userRepository.existsById(userId)) {
                UserData userData = userRepository.findById(userId).get();
                return new ResponseEntity<>(new SignInResponse(true, userData), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId !"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId !"), HttpStatus.NOT_ACCEPTABLE);
        }
        // return new ResponseEntity<>(new MessageResponse("user does not exist with this userId !", "400"), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> updateUserData(MultipartFile profileImg, MultipartFile backgroundImg, Long userId, String firstName, String description) throws IOException {
        if (userId > 0) {
            if (userRepository.existsById(userId)) {
                UserData userData = userRepository.findById(userId).get();
                UpdateUserResponse updateUserResponse = new UpdateUserResponse();
//                userData.setFirstName(updateUserRequest.getFirstName());
//                userData.setLastName(updateUserRequest.getLastName());
                if (!backgroundImg.isEmpty()) {
//                    userData.setBackgroundImgUrl(this.fileService.uploadFile(path, backgroundImg));
                    String backgroundurl = userData.getBackgroundImgUrl();
                    if (backgroundurl != null) {
                        fileService.deleteFile(backgroundurl);
                    }
                    userData.setBackgroundImgUrl(this.fileService.uploadFile(path, backgroundImg));
                    updateUserResponse.setBackgroundImg(userData.getBackgroundImgUrl());
                } else {
                    updateUserResponse.setBackgroundImg(userData.getBackgroundImgUrl());
                }
                if (!profileImg.isEmpty()) {
//                    userData.setProfileImgUrl(this.fileService.uploadFile(path, profileImg));
                    String profileurl = userData.getProfileImgUrl();
                    if (profileurl != null) {
                        fileService.deleteFile(profileurl);
                    }
                    userData.setProfileImgUrl(this.fileService.uploadFile(path, profileImg));
                    updateUserResponse.setProfileImgUrl(userData.getProfileImgUrl());
                } else {
                    // userData.setProfileImgUrl("");
                    updateUserResponse.setProfileImgUrl(userData.getProfileImgUrl());
                }
                if (firstName.isEmpty() || firstName.isBlank()) {
                    updateUserResponse.setFirstName(userData.getFirstName());
                } else {
                    userData.setFirstName(firstName);
                    updateUserResponse.setFirstName(userData.getFirstName());
                }
                if (description.isEmpty() || description.isBlank()) {
                    updateUserResponse.setDescription(userData.getDescription());
                } else {
                    userData.setDescription(description);
                    updateUserResponse.setDescription(userData.getDescription());
                }
                userRepository.save(userData);
                //  UpdateUserResponse updateUserResponse = this.modelMapper.map(updateUserRequest, UpdateUserResponse.class);
                //  updateUserResponse.setProfileImgUrl(userData.getProfileImgUrl());
                //  updateUserResponse.setCoverImgUrl(userData.getBackgroundImgUrl());
                return new ResponseEntity<>(new UpdateUserDataMessage(true, "Success", updateUserResponse), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId !"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId !"), HttpStatus.NOT_ACCEPTABLE);
        }
        //  return new ResponseEntity<>(new MessageResponse("user does not exist with this userId !", "400"), HttpStatus.BAD_REQUEST);
    }

//    @Override
//    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
//        if (userRepository.existsByEmail(forgotPasswordRequest.getEmail().toLowerCase())) {
//            UserData userData = this.userRepository.getByEmail(forgotPasswordRequest.getEmail());
//            ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse();
//            forgotPasswordResponse.setEmailId(forgotPasswordRequest.getEmail());
//            forgotPasswordResponse.setPassword(userData.getPassword());
//            return new ResponseEntity<>(new ForgotPasswordMessage(true, forgotPasswordResponse), HttpStatus.OK);
//            // return new ResponseEntity<>(new MessageResponse("Password is not matched !", "400"), HttpStatus.CONFLICT);
//        }
//        return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this email !"), HttpStatus.NOT_FOUND);
//    }

    public ResponseEntity<?> forgotPassword(String emailId) {
        if (userRepository.existsByEmail(emailId.toLowerCase())) {
//            int otp = rand.nextInt((9999 - 100) + 1) + 10;
//            String id = int.format("%04d", rand.nextInt(10000));
            int otp = rand.nextInt(9999);
            verifyOtp = otp;
            email = emailId;
            String subject = " OTP from seven ";
            String message = "OTP=" + otp;
            String to = emailId;
            boolean flag = this.emailService.sendEmail(subject, message, to);
            //   boolean flag=this.emailService.sendEmails(subject,message,to);
            if (flag) {
                return new ResponseEntity<>(new MessageResponse(true, "OTP sent Successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "something went wrong...otp not sent"), HttpStatus.EXPECTATION_FAILED);
            }

//
//            UserData userData = this.userRepository.getByEmail(emailId.toLowerCase());
//            ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse();
//            forgotPasswordResponse.setEmailId(forgotPasswordRequest.getEmail());
//            forgotPasswordResponse.setPassword(userData.getPassword());
//            return new ResponseEntity<>(new ForgotPasswordMessage(true,forgotPasswordResponse), HttpStatus.OK);
//            // return new ResponseEntity<>(new MessageResponse("Password is not matched !", "400"), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this email !"), HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<?> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        if (userRepository.existsByEmail(updatePasswordRequest.getEmail().toLowerCase())) {
            UserData userData = this.userRepository.getByEmail(updatePasswordRequest.getEmail().toLowerCase());
            if (userData.getPassword().equals(updatePasswordRequest.getPassword())) {
                userData.setPassword(updatePasswordRequest.getNewPassword());
                userRepository.save(userData);
                return new ResponseEntity<>(new MessageResponse(true, "Password updated successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Your old password is mismatch!!!"), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this email !"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        if (userId > 0) {
            if (userRepository.existsById(userId)) {
                UserData userData = userRepository.findById(userId).get();
                String profileImgUrl = userData.getProfileImgUrl();
                if (!profileImgUrl.isEmpty()) {
                    fileService.deleteFile(profileImgUrl);
                }
                List<VideoData> byUserId = videoRepository.findByUserId(userId);
                if (!byUserId.isEmpty()) {
                    for (VideoData videoData : byUserId) {
                        int videoId = videoData.getVideoId();
//                        String videoUrl = videoData.getVideoUrl();
//                        String thumbNailUrl = videoData.getThumbNailUrl();
//                        if (!videoUrl.isEmpty()) {
//                            String s = fileService.deleteFile(videoUrl);
//                            System.out.println(s);
//                        }
//                        if (!thumbNailUrl.isEmpty()) {
//                            String s1 = fileService.deleteFile(thumbNailUrl);
//                            System.out.println(s1);
//                        }
//                        videoRepository.deleteById(videoId);
                        videoService.deleteVideo(videoId);
                    }
                }
                userRepository.deleteById(userId);
                return new ResponseEntity<>(new MessageResponse(true, "User deleted successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId !"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId !"), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<?> logOutUser(Long userId) {
        if (userId > 0) {
            if (userRepository.existsById(userId)) {
                ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
                UserData userData = userRepository.findById(userId).get();
                userData.setActive(false);
                userRepository.save(userData);
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(new MessageResponse(true, "You've been signed out!"));
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId !"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId !"), HttpStatus.NOT_ACCEPTABLE);
        }
    }

//    public String showUserImageByUserId(long userId) {
//        if (userRepository.existsById(userId)) {
//            UserData userData = userRepository.findById(userId).get();
//            String profileImgUrl = userData.getProfileImgUrl();
//            String url = "https://seven02.s3.ap-south-1.amazonaws.com/" + profileImgUrl;
//            return url;
//        } else {
//            return "This user doesn't exits provide valid userid!!";
//        }
//    }


    public ResponseEntity<?> verifyOtp(int otp) {
        if (verifyOtp == otp) {
            verifyOtp = 0;
            UserData userData = userRepository.getByEmail(email);
            String subject = "Your forgot password ";
            String message = "Forget password =" + userData.getPassword();
            String to = email;
            boolean flag = this.emailService.sendPasswordToEmail(subject, message, to);
            //boolean flag=true;
            if (flag) {
                email = "";
            }
            return new ResponseEntity<>(new MessageResponse(true, "OTP verify Successfully "), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "otp not matched"), HttpStatus.CONFLICT);
        }
    }
}