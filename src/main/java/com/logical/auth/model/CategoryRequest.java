package com.logical.auth.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotNull
    @NotBlank(message="categoryName should not be blank ")
    public String categoryName;
//    @NotNull
//    @NotBlank(message="please...select categoryImage")
//    public String categoryImage;
//    public int colourCode;
}
