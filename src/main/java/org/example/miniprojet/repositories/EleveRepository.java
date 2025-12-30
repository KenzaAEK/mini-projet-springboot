package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
    // Pour la recherche d'élèves par nom
    List<Eleve> findByNom(String nom);
    // Pour la recherche d'élèves par nom
    List<Eleve> findByid(Long id);
    // Essentiel pour l'exigence : "Liste des élèves par filière"
    // Spring va chercher dans l'attribut 'filiere', puis son 'id'
    List<Eleve> findByFiliere_Id(Long id);
}