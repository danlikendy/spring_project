package com.amusementpark.repository;

import com.amusementpark.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByType(String type);
    List<Attraction> findByStatus(String status);
    List<Attraction> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT a FROM Attraction a WHERE " +
           "(:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:type IS NULL OR a.type = :type) AND " +
           "(:status IS NULL OR a.status = :status) AND " +
           "(:location IS NULL OR LOWER(a.location) LIKE LOWER(CONCAT('%', :location, '%')))")
    List<Attraction> searchAttractions(@Param("name") String name,
                                       @Param("type") String type,
                                       @Param("status") String status,
                                       @Param("location") String location);
}

