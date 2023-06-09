package com.logical.auth.model.response;

import com.logical.auth.entity.VideoData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListVideosByCategoryNameResponse {
    boolean result;
    String message;
    //List<Map<String,List<VideoData>>>data;
    Map<String,List<VideoData>>data;

}
