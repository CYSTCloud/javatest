package com.lestestes.APITEST.model;

import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookLoan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Le livre est obligatoire")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @NotNull(message = "Le membre est obligatoire")
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @NotNull(message = "La date d'emprunt est obligatoire")
    @Column(nullable = false)
    private LocalDate borrowDate;
    
    @NotNull(message = "La date de retour prevue est obligatoire")
    @Column(nullable = false)
    private LocalDate dueDate;
    
    private LocalDate returnDate;
    
    private boolean returned = false;
    
    private String notes;
    
    // Méthode pour vérifier si le prêt est en retard
    public boolean isOverdue() {
        if (returned) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }
    
    // Méthode pour calculer les jours de retard
    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }
}
