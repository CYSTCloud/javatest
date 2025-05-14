package com.lestestes.APITEST.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lestestes.APITEST.exception.ResourceNotFoundException;
import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.model.BookLoan;
import com.lestestes.APITEST.model.Member;
import com.lestestes.APITEST.repository.BookLoanRepository;
import com.lestestes.APITEST.repository.BookRepository;

@Service
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookRepository bookRepository;
    
    // Nombre maximum de livres qu'un membre peut emprunter simultanément
    private static final int MAX_LOANS_PER_MEMBER = 5;
    
    // Durée standard d'un prêt en jours
    private static final int STANDARD_LOAN_DAYS = 14;
    
    @Autowired
    public BookLoanService(BookLoanRepository bookLoanRepository, BookRepository bookRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookRepository = bookRepository;
    }
    
    // Récupérer tous les emprunts
    public List<BookLoan> getAllLoans() {
        return bookLoanRepository.findAll();
    }
    
    // Récupérer un emprunt par son ID
    public BookLoan getLoanById(Long id) {
        return bookLoanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprunt non trouvé avec l'ID : " + id));
    }
    
    // Récupérer les emprunts d'un membre
    public List<BookLoan> getLoansByMember(Member member) {
        return bookLoanRepository.findByMember(member);
    }
    
    // Récupérer les emprunts actifs d'un membre
    public List<BookLoan> getActiveLoansForMember(Member member) {
        return bookLoanRepository.findByMemberAndReturnedFalse(member);
    }
    
    // Récupérer les emprunts pour un livre
    public List<BookLoan> getLoansByBook(Book book) {
        return bookLoanRepository.findByBook(book);
    }
    
    // Récupérer tous les emprunts actifs
    public List<BookLoan> getActiveLoans() {
        return bookLoanRepository.findByReturnedFalse();
    }
    
    // Récupérer les emprunts en retard
    public List<BookLoan> getOverdueLoans() {
        return bookLoanRepository.findOverdueLoans(LocalDate.now());
    }
    
    // Créer un nouvel emprunt (emprunter un livre)
    @Transactional
    public BookLoan borrowBook(Book book, Member member, LocalDate dueDate) {
        // Vérifier si le livre est disponible
        if (!book.isAvailable()) {
            throw new IllegalStateException("Le livre n'est pas disponible pour l'emprunt");
        }
        
        // Vérifier si le livre est déjà emprunté
        if (bookLoanRepository.existsByBookAndReturnedFalse(book)) {
            throw new IllegalStateException("Le livre est déjà emprunté par un autre membre");
        }
        
        // Vérifier si le membre a déjà atteint le nombre maximum d'emprunts
        long activeLoansCount = bookLoanRepository.countByMemberAndReturnedFalse(member);
        if (activeLoansCount >= MAX_LOANS_PER_MEMBER) {
            throw new IllegalStateException("Le membre a déjà atteint le nombre maximum d'emprunts autorisés");
        }
        
        // Si aucune date de retour n'est spécifiée, utiliser la durée standard
        if (dueDate == null) {
            dueDate = LocalDate.now().plusDays(STANDARD_LOAN_DAYS);
        }
        
        // Créer un nouvel emprunt
        BookLoan loan = new BookLoan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(dueDate);
        loan.setReturned(false);
        
        // Marquer le livre comme non disponible
        book.setAvailable(false);
        bookRepository.save(book);
        
        return bookLoanRepository.save(loan);
    }
    
    // Retourner un livre
    @Transactional
    public BookLoan returnBook(Long loanId) {
        BookLoan loan = getLoanById(loanId);
        
        // Vérifier si le livre est déjà retourné
        if (loan.isReturned()) {
            throw new IllegalStateException("Ce livre a déjà été retourné");
        }
        
        // Marquer l'emprunt comme retourné
        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());
        
        // Marquer le livre comme disponible à nouveau
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);
        
        return bookLoanRepository.save(loan);
    }
    
    // Prolonger un emprunt
    @Transactional
    public BookLoan extendLoan(Long loanId, int additionalDays) {
        BookLoan loan = getLoanById(loanId);
        
        // Vérifier si le livre est déjà retourné
        if (loan.isReturned()) {
            throw new IllegalStateException("Ce livre a déjà été retourné, impossible de prolonger l'emprunt");
        }
        
        // Vérifier si l'emprunt est déjà en retard
        if (loan.isOverdue()) {
            throw new IllegalStateException("Cet emprunt est déjà en retard, impossible de le prolonger");
        }
        
        // Prolonger la date de retour
        loan.setDueDate(loan.getDueDate().plusDays(additionalDays));
        
        return bookLoanRepository.save(loan);
    }
    
    // Récupérer les statistiques d'emprunt pour une période donnée
    public List<BookLoan> getLoanStatisticsForPeriod(LocalDate startDate, LocalDate endDate) {
        return bookLoanRepository.findByBorrowDateBetween(startDate, endDate);
    }
}
