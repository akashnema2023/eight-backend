package com.logical.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalSubscriberResponse {
    boolean result;
    String meassage;
    long totalSubscriber;

    boolean subScribeStatus;

    public GetTotalSubscriberResponse(boolean result, String meassage, long totalSubscriber) {
        this.result = result;
        this.meassage = meassage;
        this.totalSubscriber = totalSubscriber;
    }
}

