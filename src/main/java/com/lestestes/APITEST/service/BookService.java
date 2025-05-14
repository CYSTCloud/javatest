package com.lestestes.APITEST.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lestestes.APITEST.exception.ResourceNotFoundException;
import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.model.Category;
import com.lestestes.APITEST.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    // Récupérer tous les livres
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // Récupérer un livre par son ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé avec l'ID : " + id));
    }
    
    // Récupérer un livre par son ISBN
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    
    // Rechercher des livres par titre
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Récupérer des livres par catégorie
    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }
    
    // Récupérer les livres disponibles
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }
    
    // Récupérer les livres par auteur
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
    
    // Créer un nouveau livre
    @Transactional
    public Book createBook(Book book) {
        // Vérifier si un livre avec le même ISBN existe déjà
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Un livre avec l'ISBN " + book.getIsbn() + " existe déjà");
        }
        return bookRepository.save(book);
    }
    
    // Mettre à jour un livre existant
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        
        // Mettre à jour les propriétés du livre
        book.setTitle(bookDetails.getTitle());
        book.setDescription(bookDetails.getDescription());
        book.setPageCount(bookDetails.getPageCount());
        book.setPublishDate(bookDetails.getPublishDate());
        book.setLanguage(bookDetails.getLanguage());
        book.setPublisher(bookDetails.getPublisher());
        book.setCoverImageUrl(bookDetails.getCoverImageUrl());
        book.setAvailable(bookDetails.isAvailable());
        
        // Mise à jour de la catégorie si elle est fournie
        if (bookDetails.getCategory() != null) {
            book.setCategory(bookDetails.getCategory());
        }
        
        // Si un nouvel ISBN est fourni et différent de l'actuel, vérifier qu'il n'existe pas déjà
        if (!book.getIsbn().equals(bookDetails.getIsbn())) {
            if (bookRepository.findByIsbn(bookDetails.getIsbn()).isPresent()) {
                throw new IllegalArgumentException("Un livre avec l'ISBN " + bookDetails.getIsbn() + " existe déjà");
            }
            book.setIsbn(bookDetails.getIsbn());
        }
        
        // Mise à jour des auteurs si fournis
        if (bookDetails.getAuthors() != null && !bookDetails.getAuthors().isEmpty()) {
            book.setAuthors(bookDetails.getAuthors());
        }
        
        return bookRepository.save(book);
    }
    
    // Supprimer un livre
    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
    
    // Changer la disponibilité d'un livre
    @Transactional
    public Book toggleAvailability(Long id) {
        Book book = getBookById(id);
        book.setAvailable(!book.isAvailable());
        return bookRepository.save(book);
    }
    
    // Récupérer les livres récemment ajoutés
    public List<Book> getRecentlyAddedBooks() {
        return bookRepository.findTop10ByOrderByPublishDateDesc();
    }
}
