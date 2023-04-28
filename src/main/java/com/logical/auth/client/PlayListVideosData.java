package com.logical.auth.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.logical.auth.enums.VideoType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="playlist_video")
public class PlayListVideosData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tempVideoId;
    int originalvideoId;
    long userId;
    int categoryId;
    String videoTitle;
    String description;
    String tag;
    VideoType videoType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date uploadDate;
    public String videoUrl;
    public String thumbNailUrl;
    public long totalViews;
    public long totalLikes;
    public boolean likeStatus;
    public boolean downloadStatus;
    public boolean subscribeStatus;
    public String userProfileUrl;
    public int playListId;
    public long playListUserId;
}
