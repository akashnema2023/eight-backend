package com.logical.auth.model.response;

import com.logical.auth.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponse {
    public boolean result;
    public String message;
    public List<SubCategory> data;
}
