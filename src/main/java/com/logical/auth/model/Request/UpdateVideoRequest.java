package com.logical.auth.model.Request;

import com.logical.auth.enums.VideoType;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateVideoRequest {
    int videoId;
    //  @OneToMany
    int categoryId;
    String videoTitle;
    String description;
    String tag;
    VideoType videoType;
}
