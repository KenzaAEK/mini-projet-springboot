package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.DossierAdministratif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierAdministratifRepository extends JpaRepository<DossierAdministratif, Long> {
    // Pas de méthode spécifique requise pour l'instant
}