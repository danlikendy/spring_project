package com.amusementpark.service;

import com.amusementpark.model.Attraction;
import com.amusementpark.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttractionService {
    
    private final AttractionRepository attractionRepository;
    
    @Autowired
    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }
    
    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }
    
    public List<Attraction> getAllAttractionsSorted(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        return attractionRepository.findAll(sort);
    }
    
    public Optional<Attraction> getAttractionById(Long id) {
        return attractionRepository.findById(id);
    }
    
    public Attraction saveAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }
    
    public void deleteAttraction(Long id) {
        attractionRepository.deleteById(id);
    }
    
    public List<Attraction> findByType(String type) {
        return attractionRepository.findByType(type);
    }
    
    public List<Attraction> findByStatus(String status) {
        return attractionRepository.findByStatus(status);
    }
    
    public List<Attraction> searchByName(String name) {
        return attractionRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Attraction> searchAttractions(String name, String type, String status, String location) {
        return attractionRepository.searchAttractions(
            name != null && !name.trim().isEmpty() ? name : null,
            type != null && !type.trim().isEmpty() ? type : null,
            status != null && !status.trim().isEmpty() ? status : null,
            location != null && !location.trim().isEmpty() ? location : null
        );
    }
    
    public long getTotalCount() {
        return attractionRepository.count();
    }
    
    public long getCountByStatus(String status) {
        return attractionRepository.findByStatus(status).size();
    }
    
    public long getCountByType(String type) {
        return attractionRepository.findByType(type).size();
    }
}

