package com.logical.auth.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="history")
public class HistoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;
    @Column(name="video_Id")
    private int videoId;
    @Column(name="user_Id")
    private long userId;
    private String videoUrl;
    public HistoryData(int videoId, long userId,String videoUrl) {
        this.videoId = videoId;
        this.userId = userId;
        this.setVideoUrl(videoUrl);
    }
}
