package com.comarch.szkolenia.FormUp.controllers;

import com.comarch.szkolenia.FormUp.model.User;
import com.comarch.szkolenia.FormUp.services.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/panel-uzytkownika")
    public String panelUzytkownika() {
        return "panel-uzytkownika";
    }

    @PostMapping("/panel-uzytkownika")
    public String updateProfile(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String surname,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) Integer age,
                                @RequestParam(required = false) Double height,
                                @RequestParam(required = false) Double weight,
                                HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) {
            return "redirect:/login";
        }

        Optional<User> updated = userService.updateProfile(
                current.getId(),
                name,
                surname,
                email,
                age,
                height,
                weight
        );

        updated.ifPresent(u -> session.setAttribute("user", u));
        return "redirect:/panel-uzytkownika";
    }

    @PostMapping("/panel-uzytkownika/zmien-haslo")
    public String changePassword(@RequestParam(required = false) String newPassword,
                                 @RequestParam(required = false, name = "confirmPassword") String confirmPassword,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        User current = (User) session.getAttribute("user");
        if (current == null) {
            return "redirect:/login";
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("pwdErr", "Hasło nie może być puste");
            return "redirect:/panel-uzytkownika";
        }
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("pwdErr", "Hasła się nie zgadzają");
            return "redirect:/panel-uzytkownika";
        }
        userService.changePassword(current.getId(), newPassword.trim());
        redirectAttributes.addFlashAttribute("pwdOk", "Hasło zostało zmienione");
        return "redirect:/panel-uzytkownika";
    }
}
