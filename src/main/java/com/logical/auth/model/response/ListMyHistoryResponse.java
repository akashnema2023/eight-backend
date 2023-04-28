package com.logical.auth.model.response;

import com.logical.auth.entity.VideoData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class ListMyHistoryResponse {
    public  boolean result;
    public String message;
    public List<VideoData>historyData;
}
