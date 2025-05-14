package com.lestestes.APITEST.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lestestes.APITEST.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Rechercher une catégorie par nom
    Optional<Category> findByNameIgnoreCase(String name);
    
    // Rechercher des catégories contenant une chaîne dans le nom
    List<Category> findByNameContainingIgnoreCase(String name);
    
    // Vérifier si une catégorie existe avec ce nom
    boolean existsByNameIgnoreCase(String name);
    
    // Trouver les catégories triées par nom
    List<Category> findAllByOrderByNameAsc();
}
