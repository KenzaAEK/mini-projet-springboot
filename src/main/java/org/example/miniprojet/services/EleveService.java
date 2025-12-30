package org.example.miniprojet.services;

import org.example.miniprojet.entities.Cours;
import org.example.miniprojet.entities.DossierAdministratif;
import org.example.miniprojet.entities.Eleve;
import org.example.miniprojet.entities.Filiere;
import org.example.miniprojet.repositories.CoursRepository;
import org.example.miniprojet.repositories.EleveRepository;
import org.example.miniprojet.repositories.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EleveService {

    private final EleveRepository eleveRepository;
    private final FiliereRepository filiereRepository;
    private final DossierService dossierService; // Ton service qui génère le string "INFO-2025-..."
    private final CoursRepository coursRepository;

    // --- LECTURE (READ) ---

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Eleve getEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + id));
    }

    public List<Eleve> chercherEleves(String keyword) {
        // Recherche partielle (LIKE %...%)
        return eleveRepository.findByNomContains(keyword);
    }

    // --- CRÉATION (AVEC DOSSIER AUTO) ---

    @Transactional
    public Eleve ajouterEleve(Eleve eleve, Long idFiliere) {
        // 1. Récupérer et associer la filière
        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new RuntimeException("Filière introuvable"));
        eleve.setFiliere(filiere);

        // 2. Sauvegarder une première fois pour générer l'ID technique
        Eleve eleveSauvegarde = eleveRepository.save(eleve);

        // 3. Créer le dossier administratif
        DossierAdministratif dossier = new DossierAdministratif();
        dossier.setDateCreation(LocalDate.now());

        // 4. Générer le numéro d'inscription formaté (Ex: INFO-2025-12)
        String numero = dossierService.genererNumeroInscription(filiere.getCode(), eleveSauvegarde.getId());
        dossier.setNumeroInscription(numero);

        // 5. Lier le dossier et sauvegarder la mise à jour
        eleveSauvegarde.setDossierAdministratif(dossier);
        return eleveRepository.save(eleveSauvegarde);
    }

    // --- MODIFICATION (SANS ÉCRASER LE DOSSIER) ---

    @Transactional
    public void modifierEleve(Eleve eleveFormulaire, Long idFiliere) {
        // 1. On récupère l'élève original en base
        Eleve eleveEnBase = getEleveById(eleveFormulaire.getId());

        // 2. On met à jour les infos simples
        eleveEnBase.setNom(eleveFormulaire.getNom());
        eleveEnBase.setPrenom(eleveFormulaire.getPrenom());

        // 3. On met à jour la filière
        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new RuntimeException("Filière introuvable"));
        eleveEnBase.setFiliere(filiere);

        // 4. On sauvegarde (le dossier administratif et les cours restent attachés)
        eleveRepository.save(eleveEnBase);
    }

    // --- GESTION DES COURS (INSCRIPTION / DÉSINSCRIPTION) ---

    @Transactional
    public void inscrireEleveAuCours(Long idEleve, Long idCours) {
        Eleve eleve = getEleveById(idEleve);
        Cours cours = coursRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        // Vérification anti-doublon
        if (eleve.getCours().contains(cours)) {
            // Optionnel : tu peux lancer une erreur ou juste ignorer
            // Ici on ignore pour ne pas faire planter l'appli si on clique deux fois
            return;
        }

        eleve.getCours().add(cours);
        eleveRepository.save(eleve);
    }

    @Transactional
    public void desinscrireEleveDuCours(Long idEleve, Long idCours) {
        Eleve eleve = getEleveById(idEleve);
        Cours cours = coursRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        // On retire le cours de la liste
        eleve.getCours().remove(cours);
        eleveRepository.save(eleve);
    }

    // --- SUPPRESSION ---

    @Transactional
    public void supprimerEleve(Long id) {
        eleveRepository.deleteById(id);
    }
}