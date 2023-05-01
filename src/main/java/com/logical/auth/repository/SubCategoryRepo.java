package com.logical.auth.repository;

import com.logical.auth.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory,Integer> {

    @Query(value = "SELECT * FROM sub_category sub WHERE sub.category_Id LIKE %?1%",nativeQuery = true)
    List<SubCategory> listOfSubCategoryByCategoryId(@RequestParam int categoryId);


//    @Query(value = "DELETE FROM sub_category c WHERE c.category_id LIKE %?1%",nativeQuery = true)
//    public void deleteSubCategoriesByCategoryId(@RequestParam int categoryId);


      List<SubCategory> findByCategoryId(int categoryId);

//      public void deleteSubCategoryByCategoryId(int categoryId);

//      List<SubCategory> findByCategoryName(String name);
}
