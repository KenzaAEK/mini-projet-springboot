package org.example.miniprojet.services;

import org.example.miniprojet.entities.Cours;
import org.example.miniprojet.entities.Filiere;
import org.example.miniprojet.repositories.CoursRepository;
import org.example.miniprojet.repositories.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // Génère le constructeur avec les arguments 'final' (Injection de dépendance propre)
public class CoursService {

    private final CoursRepository coursRepository;
    private final FiliereRepository filiereRepository;

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    // Nécessaire pour pré-remplir le formulaire de modification
    public Cours getCoursById(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours introuvable avec l'ID : " + id));
    }

    // Récupérer les cours d'une filière spécifique
    public List<Cours> getCoursParFiliere(Long idFiliere) {
        return coursRepository.findByFiliere_Id(idFiliere);
    }

    @Transactional
    public Cours ajouterCours(Cours cours, Long idFiliere) {
        // On vérifie d'abord que la filière existe
        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new RuntimeException("Impossible d'ajouter le cours : Filière introuvable"));
        // L'ASSOCIATION
        // C'est indispensable car c'est Cours porte la clé étrangère 'filiere_id' en base.
        cours.setFiliere(filiere);
        // On sauvegarde
        return coursRepository.save(cours);
    }

    public void supprimerCours(Long id) {
        coursRepository.deleteById(id);
    }
}