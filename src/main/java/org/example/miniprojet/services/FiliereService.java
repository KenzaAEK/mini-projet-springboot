package org.example.miniprojet.services;

import org.example.miniprojet.entities.Cours;
import org.example.miniprojet.entities.Eleve;
import org.example.miniprojet.entities.Filiere;
import org.example.miniprojet.repositories.CoursRepository;
import org.example.miniprojet.repositories.EleveRepository;
import org.example.miniprojet.repositories.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiliereService {

    private final FiliereRepository filiereRepository;
    private final EleveRepository eleveRepository;
    private final CoursRepository coursRepository;

    // LECTURE
    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere getFiliereById(Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filière introuvable avec l'ID : " + id));
    }

    // CREATE
    public Filiere saveFiliere(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    // SUPPRIMER AVEC LES DETACHEMENTS

    @Transactional
    public void deleteFiliere(Long id) {
        // On récupère la filière
        Filiere filiere = getFiliereById(id);

        // Traitement des ÉLÈVES : On coupe le lien
        // Si on ne fait pas ça, la base de données bloquera la suppression (Foreign Key Error)
        if (filiere.getEleves() != null) {
            for (Eleve eleve : filiere.getEleves()) {
                eleve.setFiliere(null); // L'élève devient "Sans filière"
                eleveRepository.save(eleve); // On sauvegarde le changement
            }
        }

        // Traitement des COURS : On coupe le lien
        if (filiere.getCours() != null) {
            for (Cours cours : filiere.getCours()) {
                cours.setFiliere(null);
                coursRepository.save(cours);
            }
        }

        // Maintenant que la filière est libre de toute attache, on peut la supprimer
        filiereRepository.delete(filiere);
    }
}