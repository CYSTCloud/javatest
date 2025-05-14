-- Supprimer toutes les données des tables pour avoir un état propre avant les tests
DELETE FROM "book_author";
DELETE FROM "book_loans";
DELETE FROM "books";
DELETE FROM "authors";
DELETE FROM "categories";
DELETE FROM "members";

-- Réinitialiser les séquences d'identifiants
ALTER TABLE "books" ALTER COLUMN "id" RESTART WITH 1;
ALTER TABLE "authors" ALTER COLUMN "id" RESTART WITH 1;
ALTER TABLE "categories" ALTER COLUMN "id" RESTART WITH 1;
ALTER TABLE "members" ALTER COLUMN "id" RESTART WITH 1;
ALTER TABLE "book_loans" ALTER COLUMN "id" RESTART WITH 1;
