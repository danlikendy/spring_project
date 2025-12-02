package com.amusementpark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalTime;

@Entity
@Table(name = "attractions")
public class Attraction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Название обязательно для заполнения")
    @Size(min = 2, max = 100, message = "Название должно содержать от 2 до 100 символов")
    @Column(nullable = false, length = 100)
    private String name;
    
    @NotBlank(message = "Описание обязательно для заполнения")
    @Size(min = 10, max = 500, message = "Описание должно содержать от 10 до 500 символов")
    @Column(nullable = false, length = 500)
    private String description;
    
    @NotBlank(message = "Тип аттракциона обязателен для заполнения")
    @Column(nullable = false, length = 50)
    private String type;
    
    @NotNull(message = "Минимальный возраст обязателен для заполнения")
    @Min(value = 0, message = "Минимальный возраст не может быть отрицательным")
    @Max(value = 18, message = "Минимальный возраст не может превышать 18 лет")
    @Column(nullable = false)
    private Integer minAge;
    
    @NotNull(message = "Максимальный возраст обязателен для заполнения")
    @Min(value = 0, message = "Максимальный возраст не может быть отрицательным")
    @Max(value = 100, message = "Максимальный возраст не может превышать 100 лет")
    @Column(nullable = false)
    private Integer maxAge;
    
    @NotNull(message = "Минимальный рост обязателен для заполнения")
    @DecimalMin(value = "0.0", message = "Минимальный рост не может быть отрицательным")
    @DecimalMax(value = "3.0", message = "Минимальный рост не может превышать 3 метра")
    @Column(nullable = false)
    private Double minHeight;
    
    @NotNull(message = "Время работы обязательно для заполнения")
    @Column(nullable = false)
    private LocalTime openingTime;
    
    @NotNull(message = "Время закрытия обязательно для заполнения")
    @Column(nullable = false)
    private LocalTime closingTime;
    
    @NotNull(message = "Вместимость обязательна для заполнения")
    @Min(value = 1, message = "Вместимость должна быть не менее 1")
    @Max(value = 1000, message = "Вместимость не может превышать 1000 человек")
    @Column(nullable = false)
    private Integer capacity;
    
    @NotBlank(message = "Местоположение обязательно для заполнения")
    @Size(max = 100, message = "Местоположение не должно превышать 100 символов")
    @Column(nullable = false, length = 100)
    private String location;
    
    @Column(length = 20)
    private String status;
    
    public Attraction() {
    }
    
    public Attraction(String name, String description, String type, Integer minAge, 
                     Integer maxAge, Double minHeight, LocalTime openingTime, 
                     LocalTime closingTime, Integer capacity, String location, String status) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minHeight = minHeight;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.capacity = capacity;
        this.location = location;
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Integer getMinAge() {
        return minAge;
    }
    
    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }
    
    public Integer getMaxAge() {
        return maxAge;
    }
    
    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
    
    public Double getMinHeight() {
        return minHeight;
    }
    
    public void setMinHeight(Double minHeight) {
        this.minHeight = minHeight;
    }
    
    public LocalTime getOpeningTime() {
        return openingTime;
    }
    
    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }
    
    public LocalTime getClosingTime() {
        return closingTime;
    }
    
    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}

