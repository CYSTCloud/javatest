package com.lestestes.APITEST.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.model.Category;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Rechercher un livre par ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Rechercher des livres par titre (contenant une chaîne)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Rechercher des livres par catégorie
    List<Book> findByCategory(Category category);
    
    // Rechercher des livres par disponibilité
    List<Book> findByAvailable(boolean available);
    
    // Rechercher des livres par auteur (ID de l'auteur)
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    List<Book> findByAuthorId(Long authorId);
    
    // Rechercher des livres par éditeur
    List<Book> findByPublisherContainingIgnoreCase(String publisher);
    
    // Rechercher les livres les plus récemment publiés
    List<Book> findTop10ByOrderByPublishDateDesc();
    
    // Rechercher des livres par langue
    List<Book> findByLanguageIgnoreCase(String language);
}
