-- Insertion des catégories
INSERT INTO "categories" ("name", "description") VALUES 
('Roman', 'Oeuvres de fiction narrative'),
('Science-Fiction', 'Oeuvres spéculatives basées sur des thèmes scientifiques'),
('Policier', 'Oeuvres centrées autour d''un mystère ou d''un crime'),
('Fantasy', 'Oeuvres basées sur des éléments surnaturels dans un cadre imaginaire'),
('Biographie', 'Récits de vie de personnalités');

-- Insertion des auteurs
INSERT INTO "authors" ("firstName", "lastName", "birthDate", "nationality", "biography") VALUES
('Victor', 'Hugo', '1802-02-26', 'Française', 'Victor Hugo est un poète, dramaturge et romancier français du 19e siècle.'),
('Albert', 'Camus', '1913-11-07', 'Française', 'Albert Camus était un écrivain, journaliste et philosophe français.'),
('J.K.', 'Rowling', '1965-07-31', 'Britannique', 'J.K. Rowling est surtout connue pour être l''auteure de la série Harry Potter.'),
('Agatha', 'Christie', '1890-09-15', 'Britannique', 'Agatha Christie est une romancière et auteure de théâtre britannique, spécialisée dans les romans policiers.'),
('George R.R.', 'Martin', '1948-09-20', 'Américaine', 'George R.R. Martin est un écrivain américain, principalement connu pour sa série de romans de fantasy.'),
('Isaac', 'Asimov', '1920-01-02', 'Américaine', 'Isaac Asimov était un écrivain américano-russe et un professeur de biochimie, très connu pour ses œuvres de science-fiction.');

-- Insertion des livres
INSERT INTO "books" ("title", "isbn", "description", "pageCount", "publishDate", "category_id", "language", "publisher", "coverImageUrl", "available") VALUES
('Les Misérables', '9782253096344', 'Un récit captivant qui met en lumière les injustices sociales dans la France du 19e siècle.', 1230, '1862-04-03', 1, 'Français', 'A. Lacroix, Verboeckhoven & Cie', 'https://covers.openlibrary.org/b/id/8906326-L.jpg', true),
('Harry Potter à l''école des sorciers', '9782070643028', 'Harry Potter, un jeune orphelin, découvre qu''il est en réalité un sorcier.', 308, '1997-06-26', 4, 'Français', 'Gallimard Jeunesse', 'https://covers.openlibrary.org/b/id/8267078-L.jpg', true),
('Le Trône de fer', '9782070448487', 'Dans le royaume des Sept Couronnes, la lutte pour le pouvoir est impitoyable.', 567, '1996-08-01', 4, 'Français', 'Pygmalion', 'https://covers.openlibrary.org/b/id/8311215-L.jpg', true),
('Fondation', '9782070415717', 'Premier volume du cycle de Fondation qui raconte l''effondrement d''un empire galactique.', 270, '1951-05-01', 2, 'Français', 'Gallimard', 'https://covers.openlibrary.org/b/id/10110653-L.jpg', true),
('Le Meurtrier ABC', '9782253179887', 'Le détective Hercule Poirot doit résoudre une série de meurtres alphabétiques.', 256, '1936-01-06', 3, 'Français', 'Librairie des Champs-Élysées', 'https://covers.openlibrary.org/b/id/7878010-L.jpg', true),
('L''Étranger', '9782070360024', 'Roman existentialiste qui raconte l''histoire de Meursault, un homme indifférent au monde qui l''entoure.', 184, '1942-05-19', 1, 'Français', 'Gallimard', 'https://covers.openlibrary.org/b/id/8231990-L.jpg', true);

-- Insertion des liens entre livres et auteurs
INSERT INTO "book_author" ("book_id", "author_id") VALUES
(1, 1), -- Les Misérables - Victor Hugo
(2, 3), -- Harry Potter - J.K. Rowling
(3, 5), -- Le Trône de fer - George R.R. Martin
(4, 6), -- Fondation - Isaac Asimov
(5, 4), -- Le Meurtrier ABC - Agatha Christie
(6, 2); -- L'Étranger - Albert Camus

-- Insertion des membres
INSERT INTO "members" ("firstName", "lastName", "email", "phone", "address", "birthDate", "registrationDate", "active") VALUES
('Jean', 'Dupont', 'jean.dupont@email.com', '0612345678', '15 rue des Lilas, 75020 Paris', '1985-04-12', '2022-01-15', true),
('Marie', 'Martin', 'marie.martin@email.com', '0687654321', '7 avenue Victor Hugo, 69003 Lyon', '1992-07-23', '2022-02-10', true),
('Pierre', 'Bernard', 'pierre.bernard@email.com', '0698765432', '22 rue de la Liberté, 33000 Bordeaux', '1978-11-30', '2022-03-05', true),
('Sophie', 'Petit', 'sophie.petit@email.com', '0676543210', '5 boulevard Gambetta, 59000 Lille', '1990-02-18', '2022-04-20', false),
('Lucas', 'Dubois', 'lucas.dubois@email.com', '0654321098', '12 rue Saint-Michel, 44000 Nantes', '1982-09-08', '2022-05-17', true);

-- Insertion des emprunts
INSERT INTO "book_loans" ("book_id", "member_id", "borrowDate", "dueDate", "returned", "returnDate") VALUES
(1, 1, '2023-01-10', '2023-01-24', true, '2023-01-23'),
(2, 2, '2023-02-05', '2023-02-19', true, '2023-02-15'),
(3, 3, '2023-03-12', '2023-03-26', true, '2023-04-02'),
(4, 1, '2023-04-15', '2023-04-29', false, null),
(5, 2, '2023-05-01', '2023-05-15', false, null);
