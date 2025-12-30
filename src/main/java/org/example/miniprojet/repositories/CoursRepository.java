package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    // Utile pour afficher la liste des cours proposés par une filière spécifique
    List<Cours> findByFiliere_Id(Long id);
}