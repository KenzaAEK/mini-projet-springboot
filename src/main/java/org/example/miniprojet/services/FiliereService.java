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
    // AJOUTÉ : Nécessaire pour mettre à jour les enfants (les détacher)
    private final EleveRepository eleveRepository;
    private final CoursRepository coursRepository;

    // --- LECTURE (READ) ---

    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere getFiliereById(Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filière introuvable avec l'ID : " + id));
    }

    // --- ÉCRITURE (CREATE & UPDATE) ---

    public Filiere saveFiliere(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    // --- SUPPRESSION AVEC DÉTACHEMENT (SET NULL) ---

    @Transactional // Indispensable ici car on fait plusieurs opérations d'écriture
    public void deleteFiliere(Long id) {
        // 1. On récupère la filière
        Filiere filiere = getFiliereById(id);

        // 2. Traitement des ÉLÈVES : On coupe le lien
        // Si on ne fait pas ça, la base de données bloquera la suppression (Foreign Key Error)
        if (filiere.getEleves() != null) {
            for (Eleve eleve : filiere.getEleves()) {
                eleve.setFiliere(null); // L'élève devient "Sans filière"
                eleveRepository.save(eleve); // On sauvegarde le changement
            }
        }

        // 3. Traitement des COURS : On coupe le lien
        if (filiere.getCours() != null) {
            for (Cours cours : filiere.getCours()) {
                cours.setFiliere(null); // Le cours devient "Orphelin"
                coursRepository.save(cours);
            }
        }

        // 4. Maintenant que la filière est libre de toute attache, on peut la supprimer
        filiereRepository.delete(filiere);
    }
}