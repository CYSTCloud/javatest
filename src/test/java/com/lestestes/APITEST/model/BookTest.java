package com.lestestes.APITEST.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookTest {

    private Book book1;
    private Book book2;
    private Category category;
    private Author author;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Fiction");

        author = new Author();
        author.setId(1L);
        author.setFirstName("Victor");
        author.setLastName("Hugo");

        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Les Misérables");
        book1.setIsbn("9782253096341");
        book1.setDescription("Un chef-d'œuvre de la littérature française");
        book1.setPageCount(1500);
        book1.setPublishDate(LocalDate.of(1862, 1, 1));
        book1.setCategory(category);
        book1.setLanguage("Français");
        book1.setPublisher("Albert Lacroix et Cie");
        book1.setCoverImageUrl("https://example.com/les-miserables.jpg");
        book1.setAvailable(true);
        
        Set<Author> authors = new HashSet<>();
        authors.add(author);
        book1.setAuthors(authors);
        
        // Deuxième livre avec les mêmes propriétés (pour tester equals/hashCode)
        book2 = new Book();
        book2.setId(1L);
        book2.setTitle("Les Misérables");
        book2.setIsbn("9782253096341");
        book2.setDescription("Un chef-d'œuvre de la littérature française");
        book2.setPageCount(1500);
        book2.setPublishDate(LocalDate.of(1862, 1, 1));
        book2.setCategory(category);
        book2.setLanguage("Français");
        book2.setPublisher("Albert Lacroix et Cie");
        book2.setCoverImageUrl("https://example.com/les-miserables.jpg");
        book2.setAvailable(true);
        book2.setAuthors(authors);
    }

    @Test
    @DisplayName("Test des getters et setters")
    void testGettersAndSetters() {
        assertEquals(1L, book1.getId());
        assertEquals("Les Misérables", book1.getTitle());
        assertEquals("9782253096341", book1.getIsbn());
        assertEquals("Un chef-d'œuvre de la littérature française", book1.getDescription());
        assertEquals(Integer.valueOf(1500), book1.getPageCount());
        assertEquals(LocalDate.of(1862, 1, 1), book1.getPublishDate());
        assertEquals(category, book1.getCategory());
        assertEquals("Français", book1.getLanguage());
        assertEquals("Albert Lacroix et Cie", book1.getPublisher());
        assertEquals("https://example.com/les-miserables.jpg", book1.getCoverImageUrl());
        assertTrue(book1.isAvailable());
        assertEquals(1, book1.getAuthors().size());
        assertTrue(book1.getAuthors().contains(author));
    }

    @Test
    @DisplayName("Test de equals")
    void testEquals() {
        assertEquals(book1, book2, "Deux livres avec les mêmes propriétés devraient être égaux");
        
        // Modifions une propriété pour vérifier que equals détecte la différence
        book2.setTitle("Notre-Dame de Paris");
        assertNotEquals(book1, book2, "Deux livres avec des titres différents ne devraient pas être égaux");
        
        // Vérifions que equals gère correctement la comparaison avec null et d'autres types
        assertNotEquals(book1, null, "Un livre ne devrait pas être égal à null");
        assertNotEquals(book1, "Un livre", "Un livre ne devrait pas être égal à une chaîne de caractères");
    }

    @Test
    @DisplayName("Test de hashCode")
    void testHashCode() {
        // Réinitialisons book2 pour qu'il soit identique à book1
        book2.setTitle("Les Misérables");
        
        assertEquals(book1.hashCode(), book2.hashCode(), 
                "Deux livres égaux devraient avoir le même hashCode");
        
        // Modifions une propriété et vérifions que hashCode change
        book2.setIsbn("9781234567890");
        assertNotEquals(book1.hashCode(), book2.hashCode(), 
                "Deux livres différents devraient avoir des hashCodes différents");
    }

    @Test
    @DisplayName("Test du constructeur sans argument")
    void testNoArgsConstructor() {
        Book book = new Book();
        assertNull(book.getId());
        assertNull(book.getTitle());
        assertTrue(book.isAvailable(), "Un nouveau livre devrait être disponible par défaut");
    }

    @Test
    @DisplayName("Test de toString")
    void testToString() {
        String bookString = book1.toString();
        // Vérifions que toString contient les informations essentielles
        assertTrue(bookString.contains("id=" + book1.getId()));
        assertTrue(bookString.contains("title=" + book1.getTitle()));
        assertTrue(bookString.contains("isbn=" + book1.getIsbn()));
    }
}
