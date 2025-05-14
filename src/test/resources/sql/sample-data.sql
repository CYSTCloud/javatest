-- Insertion des catégories de test
INSERT INTO "categories" ("name", "description") VALUES 
('Roman', 'Oeuvres de fiction narrative'),
('Science-Fiction', 'Oeuvres spéculatives basées sur des thèmes scientifiques'),
('Policier', 'Oeuvres centrées autour d''un mystère ou d''un crime');

-- Insertion des auteurs de test
INSERT INTO "authors" ("firstName", "lastName", "birthDate", "nationality", "biography") VALUES
('Victor', 'Hugo', '1802-02-26', 'Française', 'Victor Hugo est un poète, dramaturge et romancier français du 19e siècle.'),
('Albert', 'Camus', '1913-11-07', 'Française', 'Albert Camus était un écrivain, journaliste et philosophe français.');

-- Insertion des livres de test
INSERT INTO "books" ("title", "isbn", "description", "pageCount", "publishDate", "category_id", "language", "publisher", "coverImageUrl", "available") VALUES
('Les Misérables', '9782253096344', 'Un récit captivant qui met en lumière les injustices sociales dans la France du 19e siècle.', 1230, '1862-04-03', 1, 'Français', 'A. Lacroix, Verboeckhoven & Cie', 'https://example.com/cover1.jpg', true),
('L''Étranger', '9782070360024', 'Roman existentialiste qui raconte l''histoire de Meursault, un homme indifférent au monde qui l''entoure.', 184, '1942-05-19', 1, 'Français', 'Gallimard', 'https://example.com/cover2.jpg', true);

-- Insertion des liens entre livres et auteurs
INSERT INTO "book_author" ("book_id", "author_id") VALUES
(1, 1), -- Les Misérables - Victor Hugo
(2, 2); -- L'Étranger - Albert Camus

-- Insertion des membres de test
INSERT INTO "members" ("firstName", "lastName", "email", "phone", "address", "birthDate", "registrationDate", "active") VALUES
('Jean', 'Dupont', 'jean.test@email.com', '0612345678', '15 rue des Tests, 75020 Paris', '1985-04-12', '2022-01-15', true),
('Marie', 'Martin', 'marie.test@email.com', '0687654321', '7 avenue des Tests, 69003 Lyon', '1992-07-23', '2022-02-10', true);
