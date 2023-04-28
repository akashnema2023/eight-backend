package com.logical.auth.repository;

import com.logical.auth.entity.LikesData;
import com.logical.auth.entity.MyDownloadData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@EnableJpaRepositories
public interface MyDownloadRepo extends JpaRepository<MyDownloadData,Integer> {
   // @Query(value="SELECT * FROM download_data d2 WHERE d2.video_id LIKE %?1%", nativeQuery = true)
    //MyDownloadData getMyDownloadDataByVideoId(@RequestParam int videoId);
  // @Query(value="SELECT * FROM download_data  WHERE video_id="videoId" AND user_id="userId" ", nativeQuery = true)
    MyDownloadData findByVideoIdAndUserId(int videoId,long userId);
}
