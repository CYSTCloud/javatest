package com.lestestes.APITEST.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lestestes.APITEST.model.Book;
import com.lestestes.APITEST.model.BookLoan;
import com.lestestes.APITEST.model.Member;
import com.lestestes.APITEST.service.BookLoanService;
import com.lestestes.APITEST.service.BookService;
import com.lestestes.APITEST.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/loans")
@Tag(name = "Emprunt", description = "API de gestion des emprunts de livres")
public class BookLoanController {

    private final BookLoanService bookLoanService;
    private final BookService bookService;
    private final MemberService memberService;
    
    @Autowired
    public BookLoanController(BookLoanService bookLoanService, BookService bookService, MemberService memberService) {
        this.bookLoanService = bookLoanService;
        this.bookService = bookService;
        this.memberService = memberService;
    }
    
    @GetMapping
    @Operation(summary = "Récupérer tous les emprunts", description = "Retourne la liste de tous les emprunts de livres")
    public ResponseEntity<List<BookLoan>> getAllLoans() {
        List<BookLoan> loans = bookLoanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un emprunt par ID", description = "Retourne un emprunt unique identifié par son ID")
    public ResponseEntity<BookLoan> getLoanById(@PathVariable Long id) {
        BookLoan loan = bookLoanService.getLoanById(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Récupérer les emprunts actifs", description = "Retourne tous les emprunts actifs (non retournés)")
    public ResponseEntity<List<BookLoan>> getActiveLoans() {
        List<BookLoan> loans = bookLoanService.getActiveLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/overdue")
    @Operation(summary = "Récupérer les emprunts en retard", description = "Retourne tous les emprunts en retard")
    public ResponseEntity<List<BookLoan>> getOverdueLoans() {
        List<BookLoan> loans = bookLoanService.getOverdueLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/member/{memberId}")
    @Operation(summary = "Récupérer les emprunts d'un membre", description = "Retourne tous les emprunts pour un membre spécifique")
    public ResponseEntity<List<BookLoan>> getLoansByMember(@PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        List<BookLoan> loans = bookLoanService.getLoansByMember(member);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/member/{memberId}/active")
    @Operation(summary = "Récupérer les emprunts actifs d'un membre", description = "Retourne tous les emprunts actifs pour un membre spécifique")
    public ResponseEntity<List<BookLoan>> getActiveLoansByMember(@PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        List<BookLoan> loans = bookLoanService.getActiveLoansForMember(member);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/book/{bookId}")
    @Operation(summary = "Récupérer les emprunts d'un livre", description = "Retourne tous les emprunts pour un livre spécifique")
    public ResponseEntity<List<BookLoan>> getLoansByBook(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        List<BookLoan> loans = bookLoanService.getLoansByBook(book);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/statistics")
    @Operation(summary = "Obtenir des statistiques d'emprunt", description = "Retourne les statistiques d'emprunt pour une période spécifique")
    public ResponseEntity<List<BookLoan>> getLoanStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<BookLoan> loans = bookLoanService.getLoanStatisticsForPeriod(startDate, endDate);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @PostMapping("/borrow")
    @Operation(summary = "Emprunter un livre", description = "Crée un nouvel emprunt pour un livre par un membre")
    public ResponseEntity<BookLoan> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        
        Book book = bookService.getBookById(bookId);
        Member member = memberService.getMemberById(memberId);
        
        BookLoan loan = bookLoanService.borrowBook(book, member, dueDate);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}/return")
    @Operation(summary = "Retourner un livre", description = "Marque un emprunt comme retourné et rend le livre disponible")
    public ResponseEntity<BookLoan> returnBook(@PathVariable Long id) {
        BookLoan loan = bookLoanService.returnBook(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/extend")
    @Operation(summary = "Prolonger un emprunt", description = "Prolonge la date de retour d'un emprunt")
    public ResponseEntity<BookLoan> extendLoan(
            @PathVariable Long id,
            @RequestParam(defaultValue = "7") int days) {
        
        BookLoan loan = bookLoanService.extendLoan(id, days);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }
}
