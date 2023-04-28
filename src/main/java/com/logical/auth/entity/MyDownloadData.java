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
@Table(name="download_data")
public class MyDownloadData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int downloadId;
    @Column(name = "video_Id")
    public int videoId;
    @Column(name = "user_Id")
    public long userId;
    // public String downloadVideoUrl;
    public boolean downloadStatus;
    public MyDownloadData(int videoId, long userId, boolean downloadStatus) {
        this.videoId = videoId;
        this.userId = userId;
        //this.downloadVideoUrl=downloadVideoUrl;
        this.downloadStatus = downloadStatus;
    }
}