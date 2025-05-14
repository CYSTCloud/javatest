# Guide de lancement de l'application de gestion de bibliothèque

Ce guide vous explique comment lancer et tester l'application de gestion de bibliothèque qui comprend une API REST Spring Boot (backend) et une interface utilisateur React (frontend).

## Prérequis

- Java 17 ou supérieur
- Maven
- Node.js et npm
- Un éditeur de code (VS Code, IntelliJ IDEA, etc.)
- Git (optionnel)

## Lancement du backend (Spring Boot)

1. Ouvrez un terminal dans le répertoire racine du projet (`APITEST`)

2. Compilez et lancez l'application Spring Boot avec Maven :

   ```bash
   ./mvnw spring-boot:run
   ```

   Sous Windows, utilisez :

   ```bash
   mvnw.cmd spring-boot:run
   ```

3. Le serveur Spring Boot démarrera sur le port 8080.

4. Vous pouvez vérifier que l'API fonctionne en accédant à :
   - http://localhost:8080/swagger-ui.html - Interface Swagger pour tester les endpoints
   - http://localhost:8080/h2-console - Console H2 pour accéder à la base de données

   Pour vous connecter à la console H2, utilisez :
   - JDBC URL: `jdbc:h2:mem:bibliodb`
   - Username: `sa`
   - Password: `password`

## Lancement du frontend (React)

1. Ouvrez un nouveau terminal dans le répertoire du frontend :

   ```bash
   cd frontend
   ```

2. Installez les dépendances (à faire uniquement la première fois) :

   ```bash
   npm install
   ```

3. Lancez l'application React :

   ```bash
   npm start
   ```

4. L'application React démarrera sur le port 3000 et s'ouvrira automatiquement dans votre navigateur par défaut.
   - Si elle ne s'ouvre pas automatiquement, accédez à http://localhost:3000

## Structure des API REST

L'API propose les endpoints suivants pour chaque entité (livres, auteurs, catégories, membres, emprunts) :

### Livres (Books)
- `GET /api/books` - Récupérer tous les livres
- `GET /api/books/{id}` - Récupérer un livre par ID
- `GET /api/books/search?title={title}` - Rechercher des livres par titre
- `GET /api/books/category/{categoryId}` - Récupérer les livres par catégorie
- `GET /api/books/author/{authorId}` - Récupérer les livres par auteur
- `GET /api/books/available` - Récupérer les livres disponibles
- `GET /api/books/recent` - Récupérer les livres récemment ajoutés
- `POST /api/books` - Créer un nouveau livre
- `PUT /api/books/{id}` - Mettre à jour un livre
- `PUT /api/books/{id}/availability` - Changer la disponibilité d'un livre
- `DELETE /api/books/{id}` - Supprimer un livre

### Auteurs (Authors)
- `GET /api/authors` - Récupérer tous les auteurs
- `GET /api/authors/{id}` - Récupérer un auteur par ID
- `GET /api/authors/search?query={query}` - Rechercher des auteurs
- `GET /api/authors/nationality/{nationality}` - Récupérer les auteurs par nationalité
- `POST /api/authors` - Créer un nouvel auteur
- `PUT /api/authors/{id}` - Mettre à jour un auteur
- `DELETE /api/authors/{id}` - Supprimer un auteur

### Catégories (Categories)
- `GET /api/categories` - Récupérer toutes les catégories
- `GET /api/categories/{id}` - Récupérer une catégorie par ID
- `GET /api/categories/search?query={query}` - Rechercher des catégories
- `POST /api/categories` - Créer une nouvelle catégorie
- `PUT /api/categories/{id}` - Mettre à jour une catégorie
- `DELETE /api/categories/{id}` - Supprimer une catégorie

### Membres (Members)
- `GET /api/members` - Récupérer tous les membres
- `GET /api/members/{id}` - Récupérer un membre par ID
- `GET /api/members/search?query={query}` - Rechercher des membres
- `GET /api/members/active` - Récupérer les membres actifs
- `POST /api/members` - Créer un nouveau membre
- `PUT /api/members/{id}` - Mettre à jour un membre
- `PUT /api/members/{id}/activation` - Activer/désactiver un membre
- `DELETE /api/members/{id}` - Supprimer un membre

### Emprunts (Loans)
- `GET /api/loans` - Récupérer tous les emprunts
- `GET /api/loans/{id}` - Récupérer un emprunt par ID
- `GET /api/loans/active` - Récupérer les emprunts actifs
- `GET /api/loans/overdue` - Récupérer les emprunts en retard
- `GET /api/loans/member/{memberId}` - Récupérer les emprunts d'un membre
- `GET /api/loans/member/{memberId}/active` - Récupérer les emprunts actifs d'un membre
- `GET /api/loans/book/{bookId}` - Récupérer les emprunts d'un livre
- `POST /api/loans/borrow?bookId={bookId}&memberId={memberId}` - Emprunter un livre
- `PUT /api/loans/{id}/return` - Retourner un livre
- `PUT /api/loans/{id}/extend?days={days}` - Prolonger un emprunt

## Données initiales

L'application est initialisée avec des données de test qui comprennent :
- 5 catégories (Roman, Science-Fiction, Policier, Fantasy, Biographie)
- 6 auteurs (Victor Hugo, Albert Camus, J.K. Rowling, Agatha Christie, George R.R. Martin, Isaac Asimov)
- 6 livres (Les Misérables, Harry Potter, Le Trône de fer, Fondation, Le Meurtrier ABC, L'Étranger)
- 5 membres
- 5 emprunts (dont 3 retournés et 2 actifs)

Ces données sont chargées automatiquement au démarrage de l'application via le fichier `data.sql`.

## Prochaines étapes

Pour continuer à développer cette application, vous pourriez :

1. Implémenter les autres pages du frontend (Auteurs, Catégories, Membres, Emprunts)
2. Ajouter l'authentification et les autorisations
3. Mettre en place des tests unitaires et d'intégration
4. Déployer l'application sur un serveur
5. Migrer vers une base de données persistante (MySQL, PostgreSQL)
