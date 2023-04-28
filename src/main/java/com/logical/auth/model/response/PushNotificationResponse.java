package com.logical.auth.model.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PushNotificationResponse {
    private boolean result;
    private String message;
      @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
      private Date notificationDate;
//    private LocalDateTime notificationDate;
}