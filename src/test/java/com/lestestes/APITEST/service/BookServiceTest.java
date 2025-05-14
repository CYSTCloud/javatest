package com.lestestes.APITEST.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.model.Category;
import com.lestestes.APITEST.repository.BookRepository;
import com.lestestes.APITEST.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book1;
    private Book book2;
    private List<Book> bookList;

    @BeforeEach
    void setup() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Fiction");

        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Le Petit Prince");
        book1.setIsbn("9782070612758");
        book1.setCategory(category);
        book1.setAvailable(true);

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("L'Étranger");
        book2.setIsbn("9782070360024");
        book2.setCategory(category);
        book2.setAvailable(true);

        bookList = Arrays.asList(book1, book2);
    }

    @Test
    @DisplayName("Test pour récupérer tous les livres")
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test pour récupérer un livre par ID")
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Le Petit Prince", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test pour lancer une exception quand le livre n'existe pas")
    void testGetBookByIdNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(99L);
        });
        
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Test pour créer un livre")
    void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book result = bookService.createBook(book1);

        assertNotNull(result);
        assertEquals("Le Petit Prince", result.getTitle());
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    @DisplayName("Test pour mettre à jour un livre")
    void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Le Petit Prince (Édition Spéciale)");
        updatedBook.setIsbn("9782070612758");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(1L, updatedBook);

        assertNotNull(result);
        assertEquals("Le Petit Prince (Édition Spéciale)", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Test pour supprimer un livre")
    void testDeleteBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        doNothing().when(bookRepository).delete(any(Book.class));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(book1);
    }

    @Test
    @DisplayName("Test pour rechercher des livres par titre")
    void testSearchBooksByTitle() {
        when(bookRepository.findByTitleContainingIgnoreCase("Prince")).thenReturn(Arrays.asList(book1));

        List<Book> result = bookService.searchBooksByTitle("Prince");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Le Petit Prince", result.get(0).getTitle());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Prince");
    }

    @Test
    @DisplayName("Test pour trouver les livres disponibles")
    void testGetAvailableBooks() {
        when(bookRepository.findByAvailable(true)).thenReturn(bookList);

        List<Book> result = bookService.getAvailableBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findByAvailable(true);
    }
}
