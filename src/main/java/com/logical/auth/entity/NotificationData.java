package com.logical.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationData {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int notificationId;
    private long userId;
    private String notificationMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
//    private boolean isRead;
//    @ManyToOne
//    private UserData userData;
//    public NotificationData(String message, Date date, UserData user) {
//        this.notificationMessage=message;
//        this.createdAt=date;
//        this.userData=user;
//    }
}
