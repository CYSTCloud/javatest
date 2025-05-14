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

import com.lestestes.APITEST.model.Category;
import com.lestestes.APITEST.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Catégorie", description = "API de gestion des catégories de livres")
public class CategoryController {

    private final CategoryService categoryService;
    
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping
    @Operation(summary = "Récupérer toutes les catégories", description = "Retourne la liste de toutes les catégories de livres")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategoriesSorted();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une catégorie par ID", description = "Retourne une catégorie unique identifiée par son ID")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des catégories", description = "Recherche et retourne une liste de catégories dont le nom contient le terme de recherche")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam("query") String query) {
        List<Category> categories = categoryService.searchCategories(query);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @PostMapping
    @Operation(summary = "Créer une nouvelle catégorie", description = "Ajoute une nouvelle catégorie de livres")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une catégorie", description = "Met à jour les informations d'une catégorie existante")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une catégorie", description = "Supprime une catégorie du système")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
