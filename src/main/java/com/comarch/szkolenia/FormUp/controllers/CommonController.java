package com.comarch.szkolenia.FormUp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
