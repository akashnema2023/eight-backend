package com.logical.auth.model.response;

import com.logical.auth.entity.VideoData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyDownloadVideosResponse {
    boolean result;
    String message;
    List<VideoData>listMyVideos;
}
