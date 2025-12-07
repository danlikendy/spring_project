package com.amusementpark.controller;

import com.amusementpark.model.Attraction;
import com.amusementpark.service.AttractionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class AttractionController {
    
    private final AttractionService attractionService;
    
    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }
    
    @GetMapping
    public String getAllAttractions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String location,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            Model model) {
        
        List<Attraction> attractions;
        
        if (name != null || type != null || status != null || location != null) {
            attractions = attractionService.searchAttractions(name, type, status, location);
        } else {
            attractions = attractionService.getAllAttractions();
        }
        
        if (sortBy != null && !sortBy.isEmpty()) {
            final String finalSortBy = sortBy;
            final String finalSortDir = sortDir;
            attractions = attractions.stream()
                .sorted((a1, a2) -> {
                    int result = 0;
                    switch (finalSortBy) {
                        case "name":
                            result = a1.getName().compareToIgnoreCase(a2.getName());
                            break;
                        case "type":
                            result = a1.getType().compareToIgnoreCase(a2.getType());
                            break;
                        case "status":
                            String s1 = a1.getStatus() != null ? a1.getStatus() : "";
                            String s2 = a2.getStatus() != null ? a2.getStatus() : "";
                            result = s1.compareToIgnoreCase(s2);
                            break;
                        case "capacity":
                            result = a1.getCapacity().compareTo(a2.getCapacity());
                            break;
                        default:
                            result = a1.getId().compareTo(a2.getId());
                    }
                    return finalSortDir.equalsIgnoreCase("desc") ? -result : result;
                })
                .toList();
        }
        
        model.addAttribute("attractions", attractions);
        model.addAttribute("attraction", new Attraction());
        model.addAttribute("searchName", name != null ? name : "");
        model.addAttribute("searchType", type != null ? type : "");
        model.addAttribute("searchStatus", status != null ? status : "");
        model.addAttribute("searchLocation", location != null ? location : "");
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("totalCount", attractionService.getTotalCount());
        
        return "attractions/list";
    }
    
    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String showNewForm(Model model) {
        model.addAttribute("attraction", new Attraction());
        return "attractions/form";
    }
    
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String saveAttraction(@Valid @ModelAttribute Attraction attraction, 
                                BindingResult result, 
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("attraction", attraction);
            return "attractions/form";
        }
        
        attractionService.saveAttraction(attraction);
        redirectAttributes.addFlashAttribute("successMessage", 
            "Аттракцион успешно " + (attraction.getId() == null ? "добавлен" : "обновлен") + "!");
        return "redirect:/attractions";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return attractionService.getAttractionById(id)
            .map(attraction -> {
                model.addAttribute("attraction", attraction);
                return "attractions/form";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Аттракцион не найден!");
                return "redirect:/attractions";
            });
    }
    
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAttraction(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (attractionService.getAttractionById(id).isPresent()) {
            attractionService.deleteAttraction(id);
            redirectAttributes.addFlashAttribute("successMessage", "Аттракцион успешно удален!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Аттракцион не найден!");
        }
        return "redirect:/attractions";
    }
    
}

