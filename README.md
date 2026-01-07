# 📘 Application Web de Gestion Scolaire – Spring Boot & Thymeleaf

## 1. Introduction

Cette application Web permet de gérer une **école** avec 4 entités principales :  
- Élève  
- Filière
- Cours  
- Dossier Administratif  

**Objectifs :**  
- CRUD complet pour chaque entité avec **Thymeleaf** et **Bootstrap**  
- Relations JPA (`@OneToOne`, `@ManyToOne`, `@ManyToMany`)  
- Génération automatique du numéro d’inscription (format : FILIERE-ANNEE-CODE)

---

## 2. Architecture

Le projet utilise une **architecture en couches (N-tiers)** :  

| Couche        | Rôle principal                  |
|---------------|--------------------------------|
| Contrôleurs   | Gestion des requêtes HTTP       |
| Services      | Logique métier et transactions |
| Repositories  | Accès aux données (JPA)        |
| Base de données | MySQL + Hibernate             |

**Avantages :** Maintenable, testable, réutilisable.

---

## 3. Stack Technique

- Spring Boot 3.x  
- Spring Data JPA / Hibernate  
- Thymeleaf  
- MySQL  
- Bootstrap 5  
- Lombok  

---

## 4. Documentation

- 📄 **Rapport PDF** : [docs/Rapport_Mini_Projet_SpringBoot_AbouElKasem_Kenza.pdf](docs/Rapport_Mini_Projet_SpringBoot_AbouElKasem_Kenza.pdf)  
- 📊 **Présentation PPT** : [docs/PPT_Mini_Projet_SpringBoot_AbouElKasem_Kenza.pdf](docs/PPT_Mini_Projet_SpringBoot_AbouElKasem_Kenza.pdf)  

---

## 5. Auteur

**ABOU-EL KASEM Kenza**  
