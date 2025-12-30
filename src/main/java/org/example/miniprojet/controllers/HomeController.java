package org.example.miniprojet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:/home"; // Redirige la racine vers /home
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Affiche le joli dashboard
    }
}