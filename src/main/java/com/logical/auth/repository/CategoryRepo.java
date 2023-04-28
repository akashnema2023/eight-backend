package com.logical.auth.repository;

import com.logical.auth.entity.CategoryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@EnableJpaRepositories
public interface CategoryRepo extends JpaRepository<CategoryData,Integer> {
    @Query(value="SELECT * FROM category WHERE category_name LIKE CONCAT('%',:query,'%')",nativeQuery = true)
    List<CategoryData> searchByCategory(@Param("query") String query);
    List<CategoryData>findAllByCategoryId(int categoryId);
}
