package com.logical.auth.model.response;

import com.logical.auth.entity.CategoryData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateCategoryResponse {
    public boolean result;
    public CategoryData data;

    public CreateCategoryResponse(boolean result, CategoryData data) {
        this.result = result;
        this.data = data;
    }

}
