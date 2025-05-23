# Projet API de Gestion de Bibliothèque

Ce projet implémente une API RESTful complète pour la gestion d'une bibliothèque avec les fonctionnalités CRUD (Create, Read, Update, Delete).

## Technologies utilisées

### Backend
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Web
- Base de données H2 (pour le développement)
- Maven
- Java 17

### Frontend
- React
- Axios pour les appels API
- React Router pour la navigation
- Bootstrap pour le design

## Roadmap du projet

### Phase 1: Configuration du backend
- [x] Initialisation du projet Spring Boot
- [ ] Configuration des dépendances nécessaires
- [ ] Configuration de la base de données

### Phase 2: Implémentation du modèle de données
- [ ] Création des entités (Livre, Auteur, Catégorie, Membre, Emprunt)
- [ ] Création des repositories Spring Data JPA

### Phase 3: Développement des services et API
- [ ] Création des services métier (Business Logic)
- [ ] Développement des contrôleurs REST avec endpoints CRUD
- [ ] Configuration CORS pour permettre les appels depuis le frontend
- [ ] Documentation de l'API avec Swagger

### Phase 4: Tests du backend
- [ ] Tests unitaires des services
- [ ] Tests d'intégration des contrôleurs REST

### Phase 5: Développement du frontend
- [ ] Configuration du projet React
- [ ] Création des composants pour chaque entité
- [ ] Implémentation des formulaires pour les opérations CRUD
- [ ] Intégration avec l'API backend

### Phase 6: Finalisation
- [ ] Tests de bout en bout
- [ ] Optimisations et refactoring
- [ ] Documentation utilisateur

## Structure du projet

```
/APITEST
├── src/main/java/com/lestestes/APITEST
│   ├── controller     # Contrôleurs REST
│   ├── model          # Entités JPA
│   ├── repository     # Repositories Spring Data JPA
│   ├── service        # Services métier
│   ├── dto            # Objets de transfert de données
│   ├── config         # Configuration Spring
│   └── exception      # Gestion des exceptions
├── src/main/resources
│   ├── application.properties  # Configuration de l'application
│   └── data.sql                # Données initiales (optionnel)
└── frontend
    ├── public         # Ressources statiques
    └── src            # Code source React
```

## Installation et exécution

### Backend
```bash
# Compiler et exécuter le backend
./mvnw spring-boot:run
```

### Frontend
```bash
# Installation des dépendances
cd frontend
npm install

# Démarrer l'application React
npm start
```

L'API sera accessible à l'adresse: http://localhost:8080
L'interface frontend sera accessible à l'adresse: http://localhost:3000
#   j a v a t e s t  
 