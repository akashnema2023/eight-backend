package com.logical.auth.repository;
import com.logical.auth.entity.LikesData;
import com.logical.auth.entity.VideoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
@Repository
@EnableJpaRepositories
public interface VideoRepository extends JpaRepository<VideoData, Integer> {
    @Query(value="SELECT * FROM video v WHERE v.user_id LIKE %?1%", nativeQuery = true)
    public List<VideoData>getVideosByUserId(@RequestParam long userId);
    @Query(value="SELECT * FROM video v2 WHERE v2.category_Id LIKE %?1%", nativeQuery = true)
    public List<VideoData>getVideosByCategoryId(@RequestParam int categoryId);

    List<VideoData> findBySubCategoryId(int subCategoryId);
    public List<VideoData> findByUserId(long userId);
//  @Query(value="SELECT * FROM video v3 WHERE v3.video_title LIKE CONCAT('%',:keyword,'%')",nativeQuery = true)
    @Query(value="SELECT * FROM video v3 WHERE v3.video_title LIKE ?1%",nativeQuery = true)
    public List<VideoData> getListVideosByKeyword(@RequestParam String keyword);

    @Query(value="SELECT * FROM video v3 WHERE v3.video_title LIKE %?1%",nativeQuery = true)
    public List<VideoData> getListVideosByKeywords(@RequestParam String keyword);



}
