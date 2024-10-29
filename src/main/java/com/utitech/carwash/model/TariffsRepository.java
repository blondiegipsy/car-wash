package com.utitech.carwash.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffsRepository extends JpaRepository<Tariffs, Integer> {
}