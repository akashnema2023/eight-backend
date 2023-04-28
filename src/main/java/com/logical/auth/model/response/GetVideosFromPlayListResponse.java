package com.logical.auth.model.response;
import com.logical.auth.client.PlayListVideosData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetVideosFromPlayListResponse {
    public boolean result;
    public String message;
    List<PlayListVideosData>data;
}