package com.logical.auth.repository;

import com.logical.auth.entity.HistoryData;
import com.logical.auth.entity.MyDownloadData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface HisoryRepo extends JpaRepository<HistoryData,Integer> {
    HistoryData findByVideoIdAndUserId(int videoId, long userId);
    List<HistoryData>findListHistoryByUserId(long userId);
}
