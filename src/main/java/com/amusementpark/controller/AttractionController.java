package com.amusementpark.controller;

import com.amusementpark.model.Attraction;
import com.amusementpark.service.AttractionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getAllAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        model.addAttribute("attraction", new Attraction());
        return "attractions/list";
    }
    
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("attraction", new Attraction());
        return "attractions/form";
    }
    
    @PostMapping("/save")
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

