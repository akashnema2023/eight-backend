package com.logical.auth.repository;
import com.logical.auth.entity.PrimeVideoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PrimeRepo extends JpaRepository<PrimeVideoData,Integer> {
    public List<PrimeVideoData> getListVideoByCategoryId(int categoryId);
}
