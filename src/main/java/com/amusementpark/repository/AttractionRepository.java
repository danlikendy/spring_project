package com.amusementpark.repository;

import com.amusementpark.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByType(String type);
    List<Attraction> findByStatus(String status);
    List<Attraction> findByNameContainingIgnoreCase(String name);
}

