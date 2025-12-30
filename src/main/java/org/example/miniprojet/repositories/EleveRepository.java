package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EleveRepository extends JpaRepository<Eleve, Long> {

    // 1. Recherche par mot clé (POUR LA BARRE DE RECHERCHE)
    // "Contains" permet de trouver "Dupont" si on tape "Dup" (génère un LIKE %...% en SQL)
    List<Eleve> findByNomContains(String mc);

    // 2. Recherche par filière (POUR LES FILTRES)
    // Spring navigue : Eleve -> attribut 'filiere' -> attribut 'id'
    List<Eleve> findByFiliere_Id(Long idFiliere);
}
