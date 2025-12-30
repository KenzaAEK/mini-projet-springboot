package org.example.miniprojet.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class DossierService {
    // Génère le numéro d'inscription selon le format métier.
    public String genererNumeroInscription(String codeFiliere, String codeApogee) {
        int anneeEnCours = Year.now().getValue();
        return codeFiliere + "-" + anneeEnCours + "-" + codeApogee;
    }
}