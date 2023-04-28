package com.logical.auth.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="likes")
public class LikesData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;
    @Column(name="video_Id")
    private int videoId;
    @Column(name="user_Id")
    private long userId;
    private long totalLikes;
    private boolean likeStatus;
    public LikesData(int videoId, long userId, long totalLikes,boolean likeStatus) {
        this.videoId = videoId;
        this.userId = userId;
        this.totalLikes = totalLikes;
        this.likeStatus=likeStatus;
    }

}
