package com.logical.auth.repository;
import com.logical.auth.entity.ViewsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@EnableJpaRepositories
public interface ViewsRepo extends JpaRepository<ViewsData,String> {
  //  @Query(value = "SELECT*FROM views v where v.video_Id=videoId",nativeQuery = true)
    @Query(value="SELECT * FROM views v2 WHERE v2.video_id LIKE %?1%", nativeQuery = true)
    ViewsData getViewDataByVideoId(@RequestParam int videoId);

}
