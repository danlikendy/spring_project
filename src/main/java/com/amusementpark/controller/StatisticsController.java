package com.amusementpark.controller;

import com.amusementpark.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final AttractionService attractionService;

    @Autowired
    public StatisticsController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public String showStatistics(Model model) {
        long totalCount = attractionService.getTotalCount();
        long workingCount = attractionService.getCountByStatus("Работает");
        long maintenanceCount = attractionService.getCountByStatus("На ремонте");
        long closedCount = attractionService.getCountByStatus("Закрыт");
        
        if (totalCount == 0) {
            model.addAttribute("totalCount", 0L);
            model.addAttribute("workingCount", 0L);
            model.addAttribute("maintenanceCount", 0L);
            model.addAttribute("closedCount", 0L);
            model.addAttribute("typeStats", new HashMap<String, Long>());
            model.addAttribute("workingPercentage", 0.0);
            model.addAttribute("maintenancePercentage", 0.0);
            model.addAttribute("closedPercentage", 0.0);
            return "statistics";
        }

        Map<String, Long> typeStats = new HashMap<>();
        typeStats.put("Экстремальный", attractionService.getCountByType("Экстремальный"));
        typeStats.put("Семейный", attractionService.getCountByType("Семейный"));
        typeStats.put("Детский", attractionService.getCountByType("Детский"));
        typeStats.put("Водный", attractionService.getCountByType("Водный"));
        typeStats.put("Интерактивный", attractionService.getCountByType("Интерактивный"));
        typeStats.put("Развлекательный", attractionService.getCountByType("Развлекательный"));

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("workingCount", workingCount);
        model.addAttribute("maintenanceCount", maintenanceCount);
        model.addAttribute("closedCount", closedCount);
        model.addAttribute("typeStats", typeStats);
        
        double workingPercentage = totalCount > 0 ? (workingCount * 100.0 / totalCount) : 0;
        double maintenancePercentage = totalCount > 0 ? (maintenanceCount * 100.0 / totalCount) : 0;
        double closedPercentage = totalCount > 0 ? (closedCount * 100.0 / totalCount) : 0;
        
        double sumPercentage = workingPercentage + maintenancePercentage + closedPercentage;
        double remainingPercentage = 100.0 - sumPercentage;
        
        if (Math.abs(remainingPercentage) > 0.001) {
            if (workingCount > 0) {
                workingPercentage += remainingPercentage;
            } else if (maintenanceCount > 0) {
                maintenancePercentage += remainingPercentage;
            } else if (closedCount > 0) {
                closedPercentage += remainingPercentage;
            }
        }
        
        model.addAttribute("workingPercentage", workingPercentage);
        model.addAttribute("maintenancePercentage", maintenancePercentage);
        model.addAttribute("closedPercentage", closedPercentage);

        return "statistics";
    }
}

