package com.comarch.szkolenia.FormUp.controllers;

import com.comarch.szkolenia.FormUp.model.User;
import com.comarch.szkolenia.FormUp.services.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommonController {

    private final IUserService userService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Double bmi = userService.calculateBMI(user);
            String bmiCategory = userService.getBMICategory(bmi);
            model.addAttribute("bmi", bmi);
            model.addAttribute("bmiCategory", bmiCategory);
        }
        return "dashboard";
    }

    @GetMapping("/treningi")
    public String treningi(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("trainings", userService.getTrainingsForUser(user));
        return "treningi";
    }
}
