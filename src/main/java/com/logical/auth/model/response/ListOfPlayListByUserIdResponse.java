package com.logical.auth.model.response;

import com.logical.auth.client.PlayListVideosData;
import com.logical.auth.entity.PlayListData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ListOfPlayListByUserIdResponse {
    public boolean result;
    public String message;
    // Map<String,List<PlayListVideosData>> data;

    List<PlayListData>data;
}
