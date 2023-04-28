package com.logical.auth.repository;

import com.logical.auth.client.PlayListVideosData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayListVideosRepo extends JpaRepository<PlayListVideosData,Integer> {
    List<PlayListVideosData>findByPlayListIdAndPlayListUserId(int playListId, long userId);
    List<PlayListVideosData>findByPlayListId(int playListId);
}
