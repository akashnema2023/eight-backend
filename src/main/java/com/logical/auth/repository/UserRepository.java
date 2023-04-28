package com.logical.auth.repository;

import com.logical.auth.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData,Long> {
    boolean existsByEmail(String emailId);
    @Query(value = "SELECT * FROM user u WHERE u.email LIKE %?1%", nativeQuery = true)
    UserData getByEmail(String email);

}
