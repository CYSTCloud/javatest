package com.lestestes.APITEST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.service.BookService;
import com.lestestes.APITEST.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
@Tag(name = "Livre", description = "API de gestion des livres")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    
    @Autowired
    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }
    
    @GetMapping
    @Operation(summary = "Récupérer tous les livres", description = "Retourne la liste de tous les livres de la bibliothèque")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un livre par ID", description = "Retourne un livre unique identifié par son ID")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des livres par titre", description = "Recherche et retourne une liste de livres dont le titre contient le terme de recherche")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("title") String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Récupérer les livres par catégorie", description = "Retourne tous les livres appartenant à une catégorie spécifique")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(
            bookService.getBooksByCategory(categoryService.getCategoryById(categoryId)), 
            HttpStatus.OK
        );
    }
    
    @GetMapping("/author/{authorId}")
    @Operation(summary = "Récupérer les livres par auteur", description = "Retourne tous les livres écrits par un auteur spécifique")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Long authorId) {
        List<Book> books = bookService.getBooksByAuthorId(authorId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/available")
    @Operation(summary = "Récupérer les livres disponibles", description = "Retourne tous les livres actuellement disponibles pour l'emprunt")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> books = bookService.getAvailableBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Récupérer les livres récemment ajoutés", description = "Retourne les 10 livres les plus récemment ajoutés à la bibliothèque")
    public ResponseEntity<List<Book>> getRecentBooks() {
        List<Book> books = bookService.getRecentlyAddedBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau livre", description = "Ajoute un nouveau livre à la bibliothèque")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un livre", description = "Met à jour les informations d'un livre existant")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/availability")
    @Operation(summary = "Changer la disponibilité d'un livre", description = "Bascule l'état de disponibilité d'un livre (disponible/non disponible)")
    public ResponseEntity<Book> toggleBookAvailability(@PathVariable Long id) {
        Book book = bookService.toggleAvailability(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre", description = "Supprime un livre de la bibliothèque")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
