package com.lestestes.APITEST.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.model.Category;
import com.lestestes.APITEST.service.BookService;
import com.lestestes.APITEST.service.CategoryService;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    
    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book1;
    private Book book2;
    private List<Book> bookList;
    private Category category;

    @BeforeEach
    void setup() {
        category = new Category();
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
    @DisplayName("Test pour récupérer tous les livres - GET /api/books")
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(bookList.size()))
                .andExpect(jsonPath("$[0].id").value(book1.getId()))
                .andExpect(jsonPath("$[0].title").value(book1.getTitle()))
                .andExpect(jsonPath("$[1].id").value(book2.getId()))
                .andExpect(jsonPath("$[1].title").value(book2.getTitle()));
    }

    @Test
    @DisplayName("Test pour récupérer un livre par ID - GET /api/books/{id}")
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book1);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.title").value(book1.getTitle()))
                .andExpect(jsonPath("$.isbn").value(book1.getIsbn()));
    }

    @Test
    @DisplayName("Test pour créer un livre - POST /api/books")
    void testCreateBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book1);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.title").value(book1.getTitle()));
    }

    @Test
    @DisplayName("Test pour mettre à jour un livre - PUT /api/books/{id}")
    void testUpdateBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Le Petit Prince (Édition Spéciale)");
        updatedBook.setIsbn("9782070612758");
        updatedBook.setCategory(category);
        updatedBook.setAvailable(true);

        when(bookService.updateBook(any(Long.class), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedBook.getId()))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()));
    }

    @Test
    @DisplayName("Test pour supprimer un livre - DELETE /api/books/{id}")
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test pour rechercher des livres par titre - GET /api/books/search")
    void testSearchBooks() throws Exception {
        when(bookService.searchBooksByTitle("Prince")).thenReturn(Arrays.asList(book1));

        mockMvc.perform(get("/api/books/search")
                .param("title", "Prince"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value(book1.getTitle()));
    }

    @Test
    @DisplayName("Test pour récupérer les livres disponibles - GET /api/books/available")
    void testGetAvailableBooks() throws Exception {
        when(bookService.getAvailableBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/books/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
    
    @Test
    @DisplayName("Test pour récupérer les livres par catégorie - GET /api/books/category/{categoryId}")
    void testGetBooksByCategory() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(category);
        when(bookService.getBooksByCategory(category)).thenReturn(bookList);

        mockMvc.perform(get("/api/books/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value(book1.getTitle()));
    }

    @Test
    @DisplayName("Test pour récupérer les livres par auteur - GET /api/books/author/{authorId}")
    void testGetBooksByAuthor() throws Exception {
        when(bookService.getBooksByAuthorId(1L)).thenReturn(Arrays.asList(book1));

        mockMvc.perform(get("/api/books/author/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value(book1.getTitle()));
    }

    @Test
    @DisplayName("Test pour récupérer les livres récents - GET /api/books/recent")
    void testGetRecentBooks() throws Exception {
        when(bookService.getRecentlyAddedBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/books/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    @DisplayName("Test pour changer la disponibilité d'un livre - PUT /api/books/{id}/availability")
    void testToggleBookAvailability() throws Exception {
        Book toggledBook = new Book();
        toggledBook.setId(1L);
        toggledBook.setTitle("Le Petit Prince");
        toggledBook.setIsbn("9782070612758");
        toggledBook.setCategory(category);
        toggledBook.setAvailable(false); // Disponibilité basculée

        when(bookService.toggleAvailability(1L)).thenReturn(toggledBook);

        mockMvc.perform(put("/api/books/1/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(toggledBook.getId()))
                .andExpect(jsonPath("$.available").value(false));
    }
    
    @Test
    @DisplayName("Test pour la gestion des erreurs - GET /api/books/{id} avec ID invalide")
    void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById(999L)).thenThrow(new RuntimeException("Livre non trouvé"));

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isInternalServerError());
    }
}
