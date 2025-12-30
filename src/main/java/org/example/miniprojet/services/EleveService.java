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
    private final DossierService dossierService;
    private final CoursRepository coursRepository;

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Eleve getEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + id));
    }

    public List<Eleve> chercherEleves(String keyword) {
        // On passe le même mot-clé 3 fois pour vérifier les 3 champs
        return eleveRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrCodeApogeeContainingIgnoreCase(
                keyword, keyword, keyword
        );
    }

    @Transactional
    public Eleve ajouterEleve(Eleve eleve, Long idFiliere) {
        // Récupération de la filière
        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new RuntimeException("Filière introuvable"));
        eleve.setFiliere(filiere);

        // Génération du Code Apogée (Métier)
        String codeApogee = String.valueOf((long) (Math.random() * 90000000) + 10000000);
        eleve.setCodeApogee(codeApogee);

        // Préparation du Dossier Administratif
        DossierAdministratif dossier = new DossierAdministratif();
        dossier.setDateCreation(LocalDate.now());

        // APPEL AU SERVICE DÉDIÉ (Délégation de la logique de formatage)
        String numeroDossier = dossierService.genererNumeroInscription(filiere.getCode(), codeApogee);

        dossier.setNumeroInscription(numeroDossier);

        // Liaison et Sauvegarde en cascade
        // Grâce à CascadeType.ALL dans l'entité Eleve, sauvegarder l'élève sauvegarde aussi le dossier.
        eleve.setDossierAdministratif(dossier);

        return eleveRepository.save(eleve);
    }

    @Transactional
    public void modifierEleve(Eleve eleveFormulaire, Long idFiliere) {
        // On récupère l'élève original en base
        Eleve eleveEnBase = getEleveById(eleveFormulaire.getId());
        // On met à jour les infos simples
        eleveEnBase.setNom(eleveFormulaire.getNom());
        eleveEnBase.setPrenom(eleveFormulaire.getPrenom());
        // On met à jour la filière
        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new RuntimeException("Filière introuvable"));
        eleveEnBase.setFiliere(filiere);
        // On sauvegarde (le dossier administratif et les cours restent attachés)
        eleveRepository.save(eleveEnBase);
    }

    @Transactional
    public void inscrireEleveAuCours(Long idEleve, Long idCours) {
        Eleve eleve = getEleveById(idEleve);
        Cours cours = coursRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        // Vérification anti-doublon
        if (eleve.getCours().contains(cours)) {
            // A faire apres : lancer une erreur
            // pour le moment on va ignorer pour ne pas planter
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

    @Transactional
    public void supprimerEleve(Long id) {
        eleveRepository.deleteById(id);
    }
}