package com.logical.auth.repository;

import com.logical.auth.entity.NotificationData;
//import com.logical.auth.entity.NotificationData;
//import com.logical.auth.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//import java.awt.print.Pageable;
import java.util.List;
@Repository
public interface NotificationRepo extends JpaRepository<NotificationData,Integer> {
 //  NotificationData findByUser(UserData user);
//    @Query("select n from Notification n where n.user.uid=:userId ORDER BY n.createdAt DESC")
    List<NotificationData> findListNotificationByUserId(long userId);
//    NotificationData findByUserAndNotificationId(UserData user,int notificationId);
}