//package com.logical.auth.controller;
//import com.logical.auth.model.Request.PushNotificationRequest;
//import com.logical.auth.model.response.PushNotificationResponse;
////import com.logical.auth.services.PushNotificationService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RestController
//@RequestMapping("/notification")
//public class NotificationController {
//    private PushNotificationService pushNotificationService;
//    public NotificationController(PushNotificationService pushNotificationService) {
//        this.pushNotificationService = pushNotificationService;
//    }
//    @PostMapping("/token")
//    public ResponseEntity<?> sendTokenNotification(@RequestBody PushNotificationRequest request) {
//      try {
//          pushNotificationService.sendPushNotificationToToken(request);
//          return new ResponseEntity<>(new PushNotificationResponse(true, "Notification has been sent.",new Date()), HttpStatus.OK);
//      }
//      catch(Exception e){
//          return new ResponseEntity<>("Something went wrong...!",HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }
//}