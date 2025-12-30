package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    // Permet de vérifier l'unicité du code avant la création
    boolean existsByCode(String code);
}