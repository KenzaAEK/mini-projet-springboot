package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EleveRepository extends JpaRepository<Eleve, Long> {

    // Recherche par nom et prenom et code apogee
    List<Eleve> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrCodeApogeeContainingIgnoreCase(
            String nom, String prenom, String codeApogee
    );

    // Recherche par filière (POUR LES FILTRES)
    List<Eleve> findByFiliere_Id(Long idFiliere);
}
