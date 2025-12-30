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

    // LECTURE FILIERE
    @GetMapping("/filieres")
    public String index(Model model) {
        model.addAttribute("listFilieres", filiereService.getAllFilieres());
        return "filieres";
    }

    // AJOUTER FILIERE
    @GetMapping("/formFiliere")
    public String formFiliere(Model model) {
        // On respecte la règle du cours : objet vide pour le binding
        model.addAttribute("filiere", new Filiere());
        return "formFiliere";
    }

    // MODIFIER FILIERE
    @GetMapping("/editFiliere")
    public String editFiliere(Model model, @RequestParam(name = "id") Long id) {
        // Récupération via le service (qui contient maintenant getFiliereById)
        Filiere filiere = filiereService.getFiliereById(id);
        model.addAttribute("filiere", filiere);
        return "formFiliere";
    }

    // ENREGISTRER FILIERE
    @PostMapping("/saveFiliere")
    public String save(@ModelAttribute("filiere") Filiere filiere) {
        // @ModelAttribute gère automatiquement la récupération des données du formulaire
        filiereService.saveFiliere(filiere);
        return "redirect:/filieres";
    }

    // LECTURE ELEVES ET COURS ASSOCIES A LA FILLIERE
    @GetMapping("/detailFiliere")
    public String detailFiliere(Model model, @RequestParam(name = "id") Long id) {
        // On récupère la filière.
        // Les listes .getEleves() et .getCours() seront chargées automatiquement par Thymeleaf
        Filiere filiere = filiereService.getFiliereById(id);
        model.addAttribute("filiere", filiere);
        return "detailFiliere";
    }

    // SUPPRIMER FILLIERE
    @GetMapping("/deleteFiliere")
    public String delete(@RequestParam(name = "id") Long id) {
        filiereService.deleteFiliere(id);
        return "redirect:/filieres";
    }
}