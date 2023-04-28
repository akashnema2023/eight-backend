package com.logical.auth.repository;

import com.logical.auth.entity.SubscribeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface SubscribeRepo extends JpaRepository<SubscribeData,Integer> {
     SubscribeData findByUserIdAndSubscriberUserId(long userId,long subscriberUserId);
     @Query(value="SELECT * FROM subscribe_data WHERE user_id like %?1%",nativeQuery=true)
     List<SubscribeData>getListAllSubscriberByUserId(@RequestParam long userId);
}
