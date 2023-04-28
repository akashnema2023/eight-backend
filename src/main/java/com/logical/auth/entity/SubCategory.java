package com.logical.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subCategory")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int subCategoryId;
    public String subCategoryName;
    public int categoryId;
    @Lob
    public String subCategoryImgUrl;


}
