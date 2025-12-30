package org.example.miniprojet.controllers;

import org.example.miniprojet.entities.Cours;
import org.example.miniprojet.entities.Filiere;
import org.example.miniprojet.services.CoursService;
import org.example.miniprojet.services.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CoursController {

    private final CoursService coursService;
    private final FiliereService filiereService;

    @GetMapping("/cours")
    public String index(Model model) {
        model.addAttribute("listCours", coursService.getAllCours());
        return "cours";
    }

    @GetMapping("/formCours")
    public String formCours(Model model) {
        // Injection de l'objet de commande pour le formulaire (Binding)
        model.addAttribute("cours", new Cours());
        // Préparation de la liste des filières pour le menu déroulant (Select)
        List<Filiere> filieres = filiereService.getAllFilieres();
        model.addAttribute("filieres", filieres);
        return "formCours";
    }

    @GetMapping("/editCours")
    public String editCours(Model model, @RequestParam(name = "id") Long id) {
        // 1. On récupère le cours à modifier
        Cours cours = coursService.getCoursById(id);
        // 2. On l'injecte dans le modèle
        model.addAttribute("cours", cours);
        // 3. On doit renvoyer la liste des filières pour le menu déroulant
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "formCours"; // On réutilise la même vue que pour l'ajout
    }

    @PostMapping("/saveCours")
    public String save(@ModelAttribute("cours") Cours cours,
                       @RequestParam("filiereID") Long idFiliere) {
        coursService.ajouterCours(cours, idFiliere);
        return "redirect:/cours";
    }

    @GetMapping("/deleteCours")
    public String delete(@RequestParam(name = "id") Long id) {
        coursService.supprimerCours(id);
        return "redirect:/cours";
    }
}