package com.example.offwork.persistence.dao;

import com.example.offwork.domain.entities.ApplicationEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppRepo extends JpaRepository<ApplicationEnt,Long> {
    @Query(value = "SELECT * from applications where requested_by_id = ?1", nativeQuery = true)
    List<ApplicationEnt> findAllByRequestedById(Long id);
    @Query(value = "SELECT * from applications where requested_by_id = ?1 and status <> 'CONFIRMED'", nativeQuery = true)
    List<ApplicationEnt> findAllByRequestedByIdAndStatusNotApproved(Long id);
}
