package com.logical.auth.model.response;

import com.logical.auth.entity.VideoData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListOfVideosAccordingToCategory1 {
    boolean result;
    String message;
    //List<Map<String,List<VideoData>>>data;
    //Map<String,Map<String, List<VideoData>>> data;
    GetListVideosByCategoryNameResponse[] data;


}
