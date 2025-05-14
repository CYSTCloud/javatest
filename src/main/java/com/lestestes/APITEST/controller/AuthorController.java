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

import com.lestestes.APITEST.model.Author;
import com.lestestes.APITEST.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/authors")
@Tag(name = "Auteur", description = "API de gestion des auteurs")
public class AuthorController {

    private final AuthorService authorService;
    
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    @GetMapping
    @Operation(summary = "Récupérer tous les auteurs", description = "Retourne la liste de tous les auteurs")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthorsSorted();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un auteur par ID", description = "Retourne un auteur unique identifié par son ID")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des auteurs", description = "Recherche et retourne une liste d'auteurs dont le nom ou prénom contient le terme de recherche")
    public ResponseEntity<List<Author>> searchAuthors(@RequestParam("query") String query) {
        List<Author> authors = authorService.searchAuthors(query);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
    
    @GetMapping("/nationality/{nationality}")
    @Operation(summary = "Récupérer les auteurs par nationalité", description = "Retourne tous les auteurs d'une nationalité spécifique")
    public ResponseEntity<List<Author>> getAuthorsByNationality(@PathVariable String nationality) {
        List<Author> authors = authorService.getAuthorsByNationality(nationality);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouvel auteur", description = "Ajoute un nouvel auteur")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un auteur", description = "Met à jour les informations d'un auteur existant")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author authorDetails) {
        Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un auteur", description = "Supprime un auteur du système")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
