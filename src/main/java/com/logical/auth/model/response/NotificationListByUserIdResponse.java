package com.logical.auth.model.response;

import com.logical.auth.entity.NotificationData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class NotificationListByUserIdResponse {
    private boolean result;
    private String message;
    private List<NotificationData>data;
}
