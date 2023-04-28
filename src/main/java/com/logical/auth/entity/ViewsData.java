package com.logical.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name="views")
@Entity
public class ViewsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int viewId;
    @Column(name="video_Id")
    private int videoId;
    @Column(name="user_Id")
    private long userId;
    private long totalViews;
    public ViewsData() {
    }
    public ViewsData(int videoId, long userId, long totalViews) {
        this.videoId = videoId;
        this.userId = userId;
        this.totalViews = totalViews;
    }
}
