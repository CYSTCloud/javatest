package com.lestestes.APITEST.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lestestes.APITEST.exception.ResourceNotFoundException;
import com.lestestes.APITEST.model.Member;
import com.lestestes.APITEST.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    // Récupérer tous les membres
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    // Récupérer tous les membres triés par nom
    public List<Member> getAllMembersSorted() {
        return memberRepository.findAllByOrderByLastNameAscFirstNameAsc();
    }
    
    // Récupérer un membre par son ID
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membre non trouvé avec l'ID : " + id));
    }
    
    // Rechercher un membre par email
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    
    // Rechercher un membre par téléphone
    public Optional<Member> getMemberByPhone(String phone) {
        return memberRepository.findByPhone(phone);
    }
    
    // Rechercher des membres par nom ou prénom
    public List<Member> searchMembers(String query) {
        return memberRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(query, query);
    }
    
    // Récupérer les membres actifs
    public List<Member> getActiveMembers() {
        return memberRepository.findByActive(true);
    }
    
    // Créer un nouveau membre
    @Transactional
    public Member createMember(Member member) {
        // Vérifier si un membre avec le même email existe déjà
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("Un membre avec l'email '" + member.getEmail() + "' existe déjà");
        }
        return memberRepository.save(member);
    }
    
    // Mettre à jour un membre existant
    @Transactional
    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);
        
        // Si le nouvel email est différent de l'actuel, vérifier qu'il n'existe pas déjà
        if (!member.getEmail().equals(memberDetails.getEmail())) {
            if (memberRepository.existsByEmail(memberDetails.getEmail())) {
                throw new IllegalArgumentException("Un membre avec l'email '" + memberDetails.getEmail() + "' existe déjà");
            }
        }
        
        // Mettre à jour les propriétés du membre
        member.setFirstName(memberDetails.getFirstName());
        member.setLastName(memberDetails.getLastName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        member.setAddress(memberDetails.getAddress());
        member.setBirthDate(memberDetails.getBirthDate());
        member.setActive(memberDetails.isActive());
        
        return memberRepository.save(member);
    }
    
    // Supprimer un membre
    @Transactional
    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        
        // Vérifier si le membre a des emprunts actifs
        if (member.getLoans() != null && member.getLoans().stream().anyMatch(loan -> !loan.isReturned())) {
            throw new IllegalStateException("Impossible de supprimer le membre car il a des emprunts actifs");
        }
        
        memberRepository.delete(member);
    }
    
    // Activer/désactiver un membre
    @Transactional
    public Member toggleActivation(Long id) {
        Member member = getMemberById(id);
        member.setActive(!member.isActive());
        return memberRepository.save(member);
    }
}
