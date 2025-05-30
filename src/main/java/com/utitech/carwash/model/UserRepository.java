package com.utitech.carwash.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT SUM(u.balance) FROM User u")
    Long findTotalBalance();

    @Query("SELECT COUNT(u.username) FROM User u")
    Long getAllUsersNumber();

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);
}