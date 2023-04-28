package com.logical.auth.services;
//
//import com.logical.auth.entity.NotificationData;
//import com.logical.auth.entity.UserData;
//import com.logical.auth.repository.NotificationRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class NotificationService {
//    @Autowired
//    NotificationRepo notificationRepo;
//
//    public Map<String,Object> updateUserNotification(NotificationData notitfication, UserData user){
//
//        notitfication = this.notificationRepo.save(notitfication);
//        Map<String,Object>map=null;
//        map.put("Updated",notitfication);
////        if(notification == null){
////            return KYCUtilities._errorMultipleObject(MessageUtility.getErrorMessage("NotificationNotUpdated"), HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////
////        return KYCUtilities._successMultipleObject(ObjectMap.objectMap(notification),MessageUtility.getSuccessMessage("NotificationUpdated"));
//        return map ;
//    }
//
//    public NotificationData save(NotificationData notification){
//       // try{
//            return notificationRepo.save(notification);
////        }catch (Exception e) {
////            logger.error("Exception occur while save Notification ",e);
////            return null;
////        }
//    }
//
////
//    public NotificationData findByUser(UserData user){
////        try{
//            return notificationRepo.findByUser(user);
////        }catch (Exception e) {
////            logger.error("Exception occur while fetch Notification by User ",e);
////            return null;
////        }
//    }
////
//    public List<NotificationData> findByUsers(UserData user){
////        try{
//            return notificationRepo.userNotification(user.getUserId());
////        }catch (Exception e) {
////            logger.error("Exception occur while fetch Notification by User ",e);
////            return null;
////        }
//    }
////
//    public NotificationData createNotificationObject(String message,UserData user){
//        return new NotificationData(message,new Date(),user);
//    }
//
//   public NotificationData findByUserAndNotificationId(UserData user,int notificationId){
////        try{
//            return notificationRepo.findByUserAndNotificationId(user,notificationId);
////        }catch (Exception e) {
////            logger.error("Exception occur while fetch Notification by User and Notification Id ",e);
////            return null;
////        }
//    }
////https://www.oodlestechnologies.com/blogs/how-can-you-manage-user-notification-in-spring-boot/
//}
