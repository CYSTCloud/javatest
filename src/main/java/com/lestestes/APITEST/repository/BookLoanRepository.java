package com.lestestes.APITEST.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.model.BookLoan;
import com.lestestes.APITEST.model.Member;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    
    // Trouver tous les emprunts pour un membre spécifique
    List<BookLoan> findByMember(Member member);
    
    // Trouver tous les emprunts pour un livre spécifique
    List<BookLoan> findByBook(Book book);
    
    // Trouver les emprunts actifs (non retournés)
    List<BookLoan> findByReturnedFalse();
    
    // Trouver les emprunts en retard
    @Query("SELECT bl FROM BookLoan bl WHERE bl.returned = false AND bl.dueDate < :today")
    List<BookLoan> findOverdueLoans(LocalDate today);
    
    // Trouver les emprunts actifs pour un membre
    List<BookLoan> findByMemberAndReturnedFalse(Member member);
    
    // Trouver les emprunts retournés entre deux dates
    List<BookLoan> findByReturnedTrueAndReturnDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Trouver les emprunts créés entre deux dates
    List<BookLoan> findByBorrowDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Vérifier si un livre est actuellement emprunté
    boolean existsByBookAndReturnedFalse(Book book);
    
    // Compter le nombre d'emprunts actifs pour un membre
    long countByMemberAndReturnedFalse(Member member);
}
