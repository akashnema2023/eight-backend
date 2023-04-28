package com.logical.auth.model.response;
import com.logical.auth.entity.LikesData;
import com.logical.auth.entity.VideoData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ListOfLikesByUserId {
    boolean result;
    String message;
    List<VideoData> likesDataList;
    public ListOfLikesByUserId(boolean result, String message, List<VideoData> likesDataList) {
        this.result = result;
        this.message = message;
        this.likesDataList = likesDataList;
    }
}