package com.lestestes.APITEST.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lestestes.APITEST.exception.ResourceNotFoundException;
import com.lestestes.APITEST.model.Author;
import com.lestestes.APITEST.repository.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    
    // Récupérer tous les auteurs
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    // Récupérer tous les auteurs triés par nom
    public List<Author> getAllAuthorsSorted() {
        return authorRepository.findAllByOrderByLastNameAscFirstNameAsc();
    }
    
    // Récupérer un auteur par son ID
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auteur non trouvé avec l'ID : " + id));
    }
    
    // Rechercher des auteurs par nom ou prénom
    public List<Author> searchAuthors(String query) {
        return authorRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(query, query);
    }
    
    // Rechercher des auteurs par nationalité
    public List<Author> getAuthorsByNationality(String nationality) {
        return authorRepository.findByNationalityIgnoreCase(nationality);
    }
    
    // Créer un nouvel auteur
    @Transactional
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    // Mettre à jour un auteur existant
    @Transactional
    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = getAuthorById(id);
        
        // Mettre à jour les propriétés de l'auteur
        author.setFirstName(authorDetails.getFirstName());
        author.setLastName(authorDetails.getLastName());
        author.setBirthDate(authorDetails.getBirthDate());
        author.setBiography(authorDetails.getBiography());
        author.setNationality(authorDetails.getNationality());
        
        return authorRepository.save(author);
    }
    
    // Supprimer un auteur
    @Transactional
    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        
        // Vérifier si l'auteur a des livres associés
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer l'auteur car il a des livres associés");
        }
        
        authorRepository.delete(author);
    }
    
    // Vérifier si un auteur existe avec ce nom et prénom
    public boolean authorExists(String firstName, String lastName) {
        return authorRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
    }
}
