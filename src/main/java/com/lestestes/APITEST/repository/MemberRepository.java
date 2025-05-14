package com.lestestes.APITEST.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lestestes.APITEST.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // Rechercher un membre par email
    Optional<Member> findByEmail(String email);
    
    // Rechercher des membres par nom ou prénom
    List<Member> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);
    
    // Rechercher des membres actifs
    List<Member> findByActive(boolean active);
    
    // Vérifier si un membre existe avec cet email
    boolean existsByEmail(String email);
    
    // Rechercher des membres par numéro de téléphone
    Optional<Member> findByPhone(String phone);
    
    // Rechercher des membres par ordre alphabétique
    List<Member> findAllByOrderByLastNameAscFirstNameAsc();
}
