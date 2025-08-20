package com.comarch.szkolenia.FormUp.controllers;

import com.comarch.szkolenia.FormUp.model.Trening;
import com.comarch.szkolenia.FormUp.model.User;
import com.comarch.szkolenia.FormUp.services.IUserService;
import com.comarch.szkolenia.FormUp.services.IExerciseService;
import com.comarch.szkolenia.FormUp.controllers.dto.TrainingForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommonController {

    private final IUserService userService;
    private final IExerciseService exerciseService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Double bmi = userService.calculateBMI(user);
            String bmiCategory = userService.getBMICategory(bmi);
            model.addAttribute("bmi", bmi);
            model.addAttribute("bmiCategory", bmiCategory);

            List<Trening> trainings = userService.getTrainingsForUser(user);
            if (!trainings.isEmpty()) {
                Trening lastTraining = trainings.get(trainings.size() - 1);
                model.addAttribute("lastTraining", lastTraining);
            }
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

    @GetMapping("/trening")
    public String trening(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("exerciseNames", exerciseService.getExerciseNames());
        return "trening";
    }

    @PostMapping("/trening")
    public String saveTrening(HttpSession session, @ModelAttribute TrainingForm trainingForm) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        userService.saveTraining(user, trainingForm);
        return "redirect:/treningi";
    }
}
