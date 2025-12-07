package com.amusementpark.controller;

import com.amusementpark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Вы успешно вышли из системы");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email,
                          RedirectAttributes redirectAttributes) {
        try {
            if (userService.existsByUsername(username)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с таким именем уже существует");
                return "redirect:/register";
            }
            if (userService.existsByEmail(email)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с таким email уже существует");
                return "redirect:/register";
            }

            userService.registerUser(username, password, email);
            redirectAttributes.addFlashAttribute("successMessage", "Регистрация успешна! Войдите в систему.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка регистрации: " + e.getMessage());
            return "redirect:/register";
        }
    }
}

