package com.utitech.carwash.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    @Query(value = "SELECT * FROM log ORDER BY created_at DESC", nativeQuery = true)
    Page<Log> findAll(Pageable pageable);
}