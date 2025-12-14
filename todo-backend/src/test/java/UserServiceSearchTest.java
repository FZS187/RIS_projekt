package com.example.todobackend.service;

import com.example.todobackend.model.User;
import com.example.todobackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit testi za UserService - ČLAN 2
 *
 * Avtor: [IME ČLANA 2]
 * Datum: 12.12.2025
 *
 * Testira: ISKANJE uporabnikov po emailu
 * - Pozitiven test: Uspešno iskanje uporabnika
 * - Negativen test: Neobstoječ uporabnik
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests - Član 2: Iskanje")
class UserServiceSearchTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("hashedPassword123");
    }

    /**
     * TEST 1 - POZITIVEN
     *
     * Namen: Testiranje uspešnega iskanja obstoječega uporabnika
     *
     * Funkcionalnost: Iskanje uporabnika po email naslovu
     *
     * Scenarij:
     * 1. Kličemo findByEmail() z veljavnim emailom
     * 2. Uporabnik obstaja v bazi
     * 3. Repository vrne Optional s tem uporabnikom
     *
     * Pričakovan rezultat:
     * - Optional je prisoten (not empty)
     * - Uporabnik ima pravilen email
     * - Uporabnik ima pravilen ID
     * - Repository metoda je klicana 1x
     */
    @Test
    @DisplayName("POZITIVEN: Uspešno iskanje uporabnika po emailu")
    void testFindByEmail_UserExists() {
        // ARRANGE - Priprava podatkov
        String email = "test@example.com";

        // Mock obnašanje - uporabnik obstaja
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        // ACT - Izvršitev
        Optional<User> result = userService.findByEmail(email);

        // ASSERT - Preverjanje
        assertTrue(result.isPresent(),
                "Optional mora vsebovati uporabnika");

        assertEquals(email, result.get().getEmail(),
                "Email se mora ujemati");

        assertEquals(testUser.getId(), result.get().getId(),
                "ID se mora ujemati");

        // Preveri, da je findByEmail() klican točno 1x
        verify(userRepository, times(1)).findByEmail(email);

        System.out.println("✅ Petar Kojadinovic - POZITIVEN TEST: Uporabnik uspešno najden");
    }

    /**
     * TEST 2 - NEGATIVEN
     *
     * Namen: Testiranje iskanja neobstoječega uporabnika
     *
     * Funkcionalnost: Obravnava primera, ko uporabnik ne obstaja
     *
     * Scenarij:
     * 1. Kličemo findByEmail() z emailom
     * 2. Uporabnik NE obstaja v bazi
     * 3. Repository vrne prazen Optional
     *
     * Pričakovan rezultat:
     * - Optional je prazen (empty)
     * - isEmpty() vrne true
     * - isPresent() vrne false
     * - NE pride do napake ali izjeme
     * - Repository metoda je klicana 1x
     */
    @Test
    @DisplayName("NEGATIVEN: Iskanje neobstoječega uporabnika vrne prazen Optional")
    void testFindByEmail_UserNotFound() {
        // ARRANGE - Priprava podatkov
        String nonExistentEmail = "nonexistent@example.com";

        // Mock obnašanje - uporabnik NE obstaja
        when(userRepository.findByEmail(nonExistentEmail))
                .thenReturn(Optional.empty());

        // ACT - Izvršitev
        Optional<User> result = userService.findByEmail(nonExistentEmail);

        // ASSERT - Preverjanje
        assertFalse(result.isPresent(),
                "Optional ne sme vsebovati uporabnika");

        assertTrue(result.isEmpty(),
                "Optional mora biti prazen");

        // Preveri, da je findByEmail() klican točno 1x
        verify(userRepository, times(1)).findByEmail(nonExistentEmail);

        // Preveri, da NE pride do nobene druge operacije
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());

        System.out.println("✅ Petar Kojadinovic - NEGATIVEN TEST: Neobstoječ uporabnik pravilno obravnavan");
    }
}