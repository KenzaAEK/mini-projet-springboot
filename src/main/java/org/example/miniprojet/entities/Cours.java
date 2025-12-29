package org.example.miniprojet.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Cours {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String intitule;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    @ToString.Exclude
    private Filiere filiere;

    @ManyToMany(mappedBy = "cours")
    @ToString.Exclude
    private List<Eleve> eleves = new ArrayList<>();
}