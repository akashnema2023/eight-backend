package com.logical.auth.repository;

import com.logical.auth.entity.PlayListData;
import com.logical.auth.entity.VideoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PlayListRepo extends JpaRepository<PlayListData,Integer> {
    public PlayListData findByplayListIdAndUserId(int playListId,long userId);
//    public PlayListData findByVideoIdAndUserId(int videoId,long userId);
    public List<PlayListData>findAllPlayListByUserId(long userId);
    //public List<VideoData> findAllByplayListIdAndUserId(int playListId, long userId);
}
