package com.logical.auth.entity;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="category")
public class CategoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int categoryId;
    public String categoryName;
   // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public String categoryImageUrl;
    public String colourCode;

}
