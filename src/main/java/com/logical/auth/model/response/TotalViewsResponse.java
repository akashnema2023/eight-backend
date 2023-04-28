package com.logical.auth.model.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalViewsResponse {
    boolean result;
    String message;
    long totalViews=0;
}
