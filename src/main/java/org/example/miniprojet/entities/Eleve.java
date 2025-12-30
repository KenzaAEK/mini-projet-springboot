package org.example.miniprojet.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data // Permet de generer avec lombok les getters/setters/tostring
@NoArgsConstructor // Permet de generer avec lombok les cstrs vides (for JPA)
@AllArgsConstructor // Permet de generer les cstrs avec tous les arguments
public class Eleve {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremenet
    private Long id;

    @Column(unique = true, updatable = false)
    private String codeApogee; // ID Métier

    private String nom;
    private String prenom;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    @ToString.Exclude
    private Filiere filiere;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dossier_id")
    @ToString.Exclude
    private DossierAdministratif dossierAdministratif;

    @ManyToMany
    @JoinTable(name = "eleve_cours",
            joinColumns = @JoinColumn(name = "eleve_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_id"))
    @ToString.Exclude
    private List<Cours> cours = new ArrayList<>();
}