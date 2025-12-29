package org.example.miniprojet.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class DossierAdministratif {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroInscription;

    private LocalDate dateCreation;

    @OneToOne(mappedBy = "dossierAdministratif")
    @ToString.Exclude
    private Eleve eleve;
}