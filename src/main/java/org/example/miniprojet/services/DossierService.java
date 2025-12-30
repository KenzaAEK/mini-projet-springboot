package org.example.miniprojet.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Year;

@Service
@RequiredArgsConstructor // constructeur implicite
public class DossierService {

    // Pas de repository ici car ce service fait juste du calcul de chaîne de caractères.
    // Il est "stateless" (sans état).

    public String genererNumeroInscription(String codeFiliere, Long idEleve) {
        // Utiliser l'API Java Time moderne
        int anneeEnCours = Year.now().getValue();

        // Format demandé : INFO-2025-12
        return codeFiliere + "-" + anneeEnCours + "-" + idEleve;
    }
}