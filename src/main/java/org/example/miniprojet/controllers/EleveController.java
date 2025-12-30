package org.example.miniprojet.controllers;

import org.example.miniprojet.entities.Cours;
import org.example.miniprojet.entities.Eleve;
import org.example.miniprojet.services.CoursService;
import org.example.miniprojet.services.EleveService;
import org.example.miniprojet.services.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;
    private final FiliereService filiereService;
    private final CoursService coursService;

    // --- 1. LECTURE ET RECHERCHE ---
    @GetMapping("/eleves")
    public String index(Model model,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        // Logique de présentation : Choix de la méthode de service selon le contexte
        List<Eleve> eleves;
        if (keyword.isEmpty()) {
            eleves = eleveService.getAllEleves();
        } else {
            // Utilisation de la méthode spécifique de recherche définie dans le Service
            eleves = eleveService.chercherEleves(keyword);
        }

        // Injection des résultats dans le Modèle
        model.addAttribute("listEleves", eleves);
        model.addAttribute("keyword", keyword);
        return "eleves";
    }

    // --- 2. AFFICHAGE DÉTAILLÉ ---
    @GetMapping("/detailEleve")
    public String detailEleve(Model model, @RequestParam(name = "id") Long id) {
        // Le contrôleur récupère l'objet complet via le service
        Eleve eleve = eleveService.getEleveById(id);
        model.addAttribute("eleve", eleve);
        return "detailEleve";
    }

    // --- 3. PRÉPARATION AJOUT ---
    @GetMapping("/formEleve")
    public String formEleve(Model model) {
        // Instanciation d'un nouvel élève vide pour le binding du formulaire [cite: 42]
        model.addAttribute("eleve", new Eleve());
        // Chargement de la liste référentielle des filières pour le <select>
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "formEleve";
    }

    // --- 4. PRÉPARATION MODIFICATION ---
    @GetMapping("/editEleve")
    public String editEleve(Model model, @RequestParam(name = "id") Long id) {
        // Récupération de l'élève existant pour pré-remplissage automatique des champs (th:field)
        Eleve eleve = eleveService.getEleveById(id);

        model.addAttribute("eleve", eleve);
        // La liste des filières est toujours requise, même en modification
        model.addAttribute("filieres", filiereService.getAllFilieres());

        return "formEleve"; // Réutilisation intelligente de la vue
    }

    // --- 5. TRAITEMENT DU FORMULAIRE (ROUTAGE) ---
    @PostMapping("/saveEleve")
    public String save(@ModelAttribute("eleve") Eleve eleve,
                       @RequestParam("filiereID") Long idFiliere) {

        // Routage Métier :
        // Le contrôleur analyse l'état de l'objet (ID présent ou non) pour diriger
        // vers la bonne méthode de service. Cela respecte le principe de séparation :
        // Le contrôleur "dirige", le service "exécute".

        if (eleve.getId() == null) {
            // Cas création : Appel d'une méthode transactionnelle lourde (création dossier, etc.)
            eleveService.ajouterEleve(eleve, idFiliere);
        } else {
            // Cas modification : Appel d'une méthode de mise à jour ciblée (préservation dossier)
            eleveService.modifierEleve(eleve, idFiliere);
        }

        return "redirect:/eleves"; //
    }

    @GetMapping("/formInscription")
    public String formInscription(Model model, @RequestParam(name = "idEleve") Long idEleve) {
        Eleve eleve = eleveService.getEleveById(idEleve);
        model.addAttribute("eleve", eleve);

        List<Cours> coursFiltres;
        if (eleve.getFiliere() == null) {
            coursFiltres = List.of(); // Liste vide
        } else {
            // On charge UNIQUEMENT les cours de sa filière
            coursFiltres = coursService.getCoursParFiliere(eleve.getFiliere().getId());
            // On retire ceux où il est déjà inscrit pour éviter les doublons visuels
            coursFiltres.removeAll(eleve.getCours());
        }
        model.addAttribute("coursDisponibles", coursFiltres);

        return "formInscription";
    }

    // C'EST CETTE MÉTHODE QUI MANQUAIT ET CAUSAIT L'ERREUR 404
    @PostMapping("/saveInscription")
    public String saveInscription(@RequestParam(name = "idEleve") Long idEleve,
                                  @RequestParam(name = "idCours") Long idCours) {
        eleveService.inscrireEleveAuCours(idEleve, idCours);
        // On redirige vers la page de détails pour voir le résultat
        return "redirect:/detailEleve?id=" + idEleve;
    }

    @GetMapping("/desinscrireCours")
    public String desinscrire(@RequestParam(name = "idEleve") Long idEleve,
                              @RequestParam(name = "idCours") Long idCours) {
        eleveService.desinscrireEleveDuCours(idEleve, idCours);
        return "redirect:/detailEleve?id=" + idEleve;
    }

    // --- 6. SUPPRESSION ---
    @GetMapping("/deleteEleve")
    public String delete(@RequestParam(name = "id") Long id) {
        eleveService.supprimerEleve(id);
        return "redirect:/eleves";
    }
}