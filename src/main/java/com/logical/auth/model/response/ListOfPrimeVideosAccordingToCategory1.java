package com.logical.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListOfPrimeVideosAccordingToCategory1 {
    boolean result;
    String message;
    //List<Map<String,List<VideoData>>>data;
    //Map<String,Map<String, List<VideoData>>> data;
    GetListPrimeVideosByCategoryNameResponse[] data;

}
