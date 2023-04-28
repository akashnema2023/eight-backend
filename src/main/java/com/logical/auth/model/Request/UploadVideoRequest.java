package com.logical.auth.model.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.logical.auth.enums.VideoType;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadVideoRequest {
    int videoId;
    //  @OneToMany
    int categoryId;
    String videoTitle;
    String description;
    String tag;
    VideoType videoType;
}
