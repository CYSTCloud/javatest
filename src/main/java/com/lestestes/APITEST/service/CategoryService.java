package com.lestestes.APITEST.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lestestes.APITEST.exception.ResourceNotFoundException;
import com.lestestes.APITEST.model.Category;
import com.lestestes.APITEST.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    // Récupérer toutes les catégories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    // Récupérer toutes les catégories triées par nom
    public List<Category> getAllCategoriesSorted() {
        return categoryRepository.findAllByOrderByNameAsc();
    }
    
    // Récupérer une catégorie par son ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID : " + id));
    }
    
    // Rechercher une catégorie par son nom
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }
    
    // Rechercher des catégories par nom contenant une chaîne
    public List<Category> searchCategories(String query) {
        return categoryRepository.findByNameContainingIgnoreCase(query);
    }
    
    // Créer une nouvelle catégorie
    @Transactional
    public Category createCategory(Category category) {
        // Vérifier si une catégorie avec le même nom existe déjà
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Une catégorie avec le nom '" + category.getName() + "' existe déjà");
        }
        return categoryRepository.save(category);
    }
    
    // Mettre à jour une catégorie existante
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        
        // Si le nouveau nom est différent de l'actuel, vérifier qu'il n'existe pas déjà
        if (!category.getName().equalsIgnoreCase(categoryDetails.getName())) {
            if (categoryRepository.existsByNameIgnoreCase(categoryDetails.getName())) {
                throw new IllegalArgumentException("Une catégorie avec le nom '" + categoryDetails.getName() + "' existe déjà");
            }
        }
        
        // Mettre à jour les propriétés de la catégorie
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        
        return categoryRepository.save(category);
    }
    
    // Supprimer une catégorie
    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        
        // Vérifier si la catégorie a des livres associés
        if (category.getBooks() != null && !category.getBooks().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer la catégorie car elle contient des livres");
        }
        
        categoryRepository.delete(category);
    }
    
    // Vérifier si une catégorie existe avec ce nom
    public boolean categoryExists(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }
}
