package com.lestestes.APITEST.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {

    private Member member1;
    private Member member2;
    private Set<BookLoan> loans;

    @BeforeEach
    void setUp() {
        loans = new HashSet<>();
        
        member1 = new Member();
        member1.setId(1L);
        member1.setFirstName("Jean");
        member1.setLastName("Dupont");
        member1.setEmail("jean.dupont@example.com");
        member1.setPhone("0123456789");
        member1.setAddress("1 rue de la Paix");
        member1.setRegistrationDate(LocalDate.of(2020, 1, 1));
        member1.setActive(true);
        member1.setLoans(loans);
        
        // Deuxième membre avec les mêmes propriétés pour les tests d'égalité
        member2 = new Member();
        member2.setId(1L);
        member2.setFirstName("Jean");
        member2.setLastName("Dupont");
        member2.setEmail("jean.dupont@example.com");
        member2.setPhone("0123456789");
        member2.setAddress("1 rue de la Paix");
        member2.setRegistrationDate(LocalDate.of(2020, 1, 1));
        member2.setActive(true);
        member2.setLoans(loans);
    }

    @Test
    @DisplayName("Test des getters et setters")
    void testGettersAndSetters() {
        assertEquals(1L, member1.getId());
        assertEquals("Jean", member1.getFirstName());
        assertEquals("Dupont", member1.getLastName());
        assertEquals("jean.dupont@example.com", member1.getEmail());
        assertEquals("0123456789", member1.getPhone());
        assertEquals("1 rue de la Paix", member1.getAddress());
        assertEquals(LocalDate.of(2020, 1, 1), member1.getRegistrationDate());
        assertTrue(member1.isActive());
        assertEquals(loans, member1.getLoans());
    }

    @Test
    @DisplayName("Test de equals")
    void testEquals() {
        assertEquals(member1, member2, "Deux membres avec les mêmes propriétés devraient être égaux");
        
        // Modification d'une propriété pour tester la différence
        member2.setEmail("jean.martin@example.com");
        assertNotEquals(member1, member2, "Deux membres avec des emails différents ne devraient pas être égaux");
        
        // Test avec null et autres types
        assertNotEquals(member1, null, "Un membre ne devrait pas être égal à null");
        assertNotEquals(member1, "Un membre", "Un membre ne devrait pas être égal à une chaîne de caractères");
    }

    @Test
    @DisplayName("Test de hashCode")
    void testHashCode() {
        // Réinitialisation du membre2 pour le rendre identique au membre1
        member2.setEmail("jean.dupont@example.com");
        
        assertEquals(member1.hashCode(), member2.hashCode(), 
                "Deux membres égaux devraient avoir le même hashCode");
        
        // Modification d'une propriété et vérification du changement de hashCode
        member2.setFirstName("Pierre");
        assertNotEquals(member1.hashCode(), member2.hashCode(), 
                "Deux membres différents devraient avoir des hashCodes différents");
    }

    @Test
    @DisplayName("Test du constructeur sans argument")
    void testNoArgsConstructor() {
        Member member = new Member();
        assertNull(member.getId());
        assertNull(member.getFirstName());
        assertNull(member.getEmail());
        assertTrue(member.isActive(), "Un nouveau membre est actif par défaut");
    }

    @Test
    @DisplayName("Test de toString")
    void testToString() {
        String memberString = member1.toString();
        
        // Vérification que toString contient les informations essentielles
        assertTrue(memberString.contains("id=" + member1.getId()));
        assertTrue(memberString.contains("firstName=" + member1.getFirstName()));
        assertTrue(memberString.contains("lastName=" + member1.getLastName()));
        assertTrue(memberString.contains("email=" + member1.getEmail()));
    }
}
