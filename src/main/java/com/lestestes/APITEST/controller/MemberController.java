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

import com.lestestes.APITEST.model.Member;
import com.lestestes.APITEST.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/members")
@Tag(name = "Membre", description = "API de gestion des membres de la bibliothèque")
public class MemberController {

    private final MemberService memberService;
    
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    @GetMapping
    @Operation(summary = "Récupérer tous les membres", description = "Retourne la liste de tous les membres de la bibliothèque")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembersSorted();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un membre par ID", description = "Retourne un membre unique identifié par son ID")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des membres", description = "Recherche et retourne une liste de membres dont le nom ou prénom contient le terme de recherche")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam("query") String query) {
        List<Member> members = memberService.searchMembers(query);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Récupérer les membres actifs", description = "Retourne tous les membres actifs de la bibliothèque")
    public ResponseEntity<List<Member>> getActiveMembers() {
        List<Member> members = memberService.getActiveMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau membre", description = "Ajoute un nouveau membre à la bibliothèque")
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un membre", description = "Met à jour les informations d'un membre existant")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @Valid @RequestBody Member memberDetails) {
        Member updatedMember = memberService.updateMember(id, memberDetails);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/activation")
    @Operation(summary = "Activer/désactiver un membre", description = "Bascule l'état d'activation d'un membre (actif/inactif)")
    public ResponseEntity<Member> toggleMemberActivation(@PathVariable Long id) {
        Member member = memberService.toggleActivation(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un membre", description = "Supprime un membre de la bibliothèque")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
