package com.example.offwork.persistence.dao;
import com.example.offwork.domain.entities.UserEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEnt, Long> {

    @Query(value = "SELECT * from users where email = ?1 or username = ?1", nativeQuery = true)
    Optional<UserEnt> getByUSernameOrEmail(String username);

}
