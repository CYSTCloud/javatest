package com.lestestes.APITEST.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lestestes.APITEST.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    // Rechercher des auteurs par nom ou prénom
    List<Author> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);
    
    // Rechercher des auteurs par nationalité
    List<Author> findByNationalityIgnoreCase(String nationality);
    
    // Rechercher des auteurs par ordre alphabétique
    List<Author> findAllByOrderByLastNameAscFirstNameAsc();
    
    // Vérifier si un auteur existe avec ce nom et prénom
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
