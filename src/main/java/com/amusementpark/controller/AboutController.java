package com.amusementpark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("authorName", "Попов Александр Алексеевич");
        model.addAttribute("group", "ИТМ22-4");
        model.addAttribute("university", "Финансовый университет при Правительстве РФ");
        model.addAttribute("faculty", "Факультет информационных технологий и анализа больших данных");
        model.addAttribute("department", "Кафедра анализа данных и машинного обучения");
        model.addAttribute("email", "alex.popov@example.com");
        model.addAttribute("technologies", new String[]{
            "Java 17",
            "Spring Boot 3.2.0",
            "Spring Security",
            "Spring Data JPA",
            "Hibernate",
            "Thymeleaf",
            "H2 Database",
            "Maven",
            "HTML/CSS"
        });
        return "about";
    }
}

