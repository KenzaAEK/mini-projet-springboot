package org.example.miniprojet.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Filiere {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String nom;

    @OneToMany(mappedBy = "filiere")
    @ToString.Exclude
    private List<Eleve> eleves = new ArrayList<>();

    @OneToMany(mappedBy = "filiere")
    @ToString.Exclude
    private List<Cours> cours = new ArrayList<>();
}