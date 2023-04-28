package com.logical.auth.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {
        @NotNull
        @NotBlank(message="categoryName should not be blank ")
        public String categoryName;
        @NotNull
        @NotBlank(message="please...select categoryImage")
        public String categoryImage;
        public String colourCode;

}
