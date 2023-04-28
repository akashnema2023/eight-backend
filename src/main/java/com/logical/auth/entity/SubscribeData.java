package com.logical.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="subscribe_data")
public class SubscribeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int subscribeId;
    public long userId;
    public long subscriberUserId;
    public boolean subscribeStatus;

    public SubscribeData(long userId, long subscriberUserId, boolean subscribeStatus) {
        this.userId = userId;
        this.subscriberUserId = subscriberUserId;
        this.subscribeStatus = subscribeStatus;
    }
}
