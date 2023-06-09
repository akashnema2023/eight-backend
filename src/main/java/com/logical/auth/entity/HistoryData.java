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
    @Column(name = "username")
    private String userName;
    private String videoUrl;
    public HistoryData(int videoId, long userId,String userName,String videoUrl) {
        this.videoId = videoId;
        this.userId = userId;
        this.userName=userName;
        this.setVideoUrl(videoUrl);
    }
}
