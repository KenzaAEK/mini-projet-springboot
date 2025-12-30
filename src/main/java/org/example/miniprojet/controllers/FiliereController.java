package org.example.miniprojet.controllers;

import org.example.miniprojet.entities.Filiere;
import org.example.miniprojet.services.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class FiliereController {

    private final FiliereService filiereService;

    // --- LECTURE (READ) ---
    @GetMapping("/filieres")
    public String index(Model model) {
        model.addAttribute("listFilieres", filiereService.getAllFilieres());
        return "filieres";
    }

    // --- FORMULAIRE D'AJOUT (CREATE) ---
    @GetMapping("/formFiliere")
    public String formFiliere(Model model) {
        // On respecte la règle du cours : objet vide pour le binding
        model.addAttribute("filiere", new Filiere());
        return "formFiliere";
    }

    // --- FORMULAIRE D'ÉDITION (UPDATE) ---
    @GetMapping("/editFiliere")
    public String editFiliere(Model model, @RequestParam(name = "id") Long id) {
        // Récupération via le service (qui contient maintenant getFiliereById)
        Filiere filiere = filiereService.getFiliereById(id);
        model.addAttribute("filiere", filiere);
        return "formFiliere";
    }

    // --- SAUVEGARDE (CREATE & UPDATE) ---
    @PostMapping("/saveFiliere")
    public String save(@ModelAttribute("filiere") Filiere filiere) {
        // @ModelAttribute gère automatiquement la récupération des données du formulaire
        filiereService.saveFiliere(filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/detailFiliere")
    public String detailFiliere(Model model, @RequestParam(name = "id") Long id) {
        // On récupère la filière.
        // Les listes .getEleves() et .getCours() seront chargées automatiquement par Thymeleaf
        Filiere filiere = filiereService.getFiliereById(id);
        model.addAttribute("filiere", filiere);
        return "detailFiliere";
    }

    // --- SUPPRESSION (DELETE - Version "Set Null") ---
    @GetMapping("/deleteFiliere")
    public String delete(@RequestParam(name = "id") Long id) {
        // Plus besoin de try-catch !
        // Le service va automatiquement détacher les élèves (set null) avant de supprimer.
        filiereService.deleteFiliere(id);

        return "redirect:/filieres";
    }
}