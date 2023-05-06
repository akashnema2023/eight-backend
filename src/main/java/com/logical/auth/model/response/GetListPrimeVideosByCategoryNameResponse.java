package com.logical.auth.model.response;
import com.logical.auth.entity.VideoData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetListPrimeVideosByCategoryNameResponse {
    int categoryId;
    String categoryName;
    Set<VideoData> categoryData;
}
