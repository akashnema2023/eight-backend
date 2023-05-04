package com.logical.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.logical.auth.enums.VideoType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="PrimeVideoData")
public class PrimeVideoData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int primeId;
    int videoId;
    // @OneToMany
    long userId;
    //  @OneToMany
    String userName;
    int categoryId;
    int subCategoryId;
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
}
