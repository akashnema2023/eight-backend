package com.logical.auth.model.response;

import com.logical.auth.client.PlayListVideosData;
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
public class GetPlayListWithVideosByUserIdResponse {
    public boolean result;
    public String message;
    Map<String,List<PlayListVideosData>> data;
}
