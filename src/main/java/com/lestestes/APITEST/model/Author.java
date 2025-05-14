package com.lestestes.APITEST.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le prenom est obligatoire")
    @Column(nullable = false)
    private String firstName;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String lastName;
    
    @PastOrPresent(message = "La date de naissance ne peut pas etre dans le futur")
    private LocalDate birthDate;
    
    private String biography;
    
    private String nationality;
    
    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
    
    // Méthode utilitaire pour obtenir le nom complet
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
