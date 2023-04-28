package com.logical.auth.repository;

import com.logical.auth.entity.LikesData;
import com.logical.auth.entity.ViewsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface LikesRepo extends JpaRepository<LikesData,Integer> {
    @Query(value="SELECT * FROM likes l2 WHERE l2.video_id LIKE %?1%", nativeQuery = true)
    LikesData getLikesDataByVideoId(@RequestParam int videoId);
    @Query(value = "SELECT * FROM likes l3 where l3.user_id LIKE %?1%",nativeQuery = true)
    public List<LikesData> getMyLikesByUserId(@RequestParam long userId);
}
