package org.example.miniprojet;

import org.example.miniprojet.entities.Eleve;
import org.example.miniprojet.entities.Filiere;
import org.example.miniprojet.services.EleveService;
import org.example.miniprojet.services.FiliereService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MiniProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniProjetApplication.class, args);
    }


}
