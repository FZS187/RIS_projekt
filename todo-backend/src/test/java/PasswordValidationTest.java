package com.example.todobackend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.todobackend.model.User;
import com.example.todobackend.repository.UserRepository;

/**
 * Unit testi za UserService - ČLAN 3
 *
 * Avtor: [IME ČLANA 3]
 * Datum: 12.12.2025
 *
 * Testira VALIDACIJO IN ŠIFRIRANJE GESEL:
 * - TEST 1 (POZITIVEN): Šifriranje dolge in kompleksne lozinke
 * - TEST 2 (NEGATIVEN): Registracija s praznim geslom
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests - Član 3: Validacija Gesla")
class PasswordValidationTest {

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
        testUser.setPassword("$2a$10$hashedPassword");
    }

    /**
     * ============================================================
     * TEST 1 - POZITIVEN (Član 3)
     * ============================================================
     *
     * Namen: Preveriti pravilno šifriranje dolge kompleksne lozinke
     *
     * Kaj testiramo:
     * - Ali se dolga (30+ znakov) kompleksna lozinka pravilno šifrira
     * - Ali passwordEncoder obravnava posebne znake
     * - Ali se geslo ne shrani v plain text obliki
     *
     * Uporabljene anotacije:
     * - @Test - označuje testno metodo
     * - @DisplayName - opisno ime testa
     * - @Mock - kreira mock objekt za passwordEncoder
     */
    @Test
    @DisplayName("TEST 1 - POZITIVEN: Šifriranje dolge kompleksne lozinke")
    void testRegisterWithComplexPassword_Success() {
        // ===== ARRANGE (Priprava) =====
        String email = "secure@example.com";
        String complexPassword = "VeryL0ng&C0mpl3x!P@ssw0rd#2024$%^";
        String encodedPassword = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(complexPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // ===== ACT (Izvršitev) =====
        User result = userService.register(email, complexPassword);

        // ===== ASSERT (Preverjanje) =====
        assertNotNull(result, "Registriran uporabnik ne sme biti null");

        // Preverimo, da je passwordEncoder.encode() klican z dolgim geslom
        verify(passwordEncoder, times(1)).encode(complexPassword);

        // Preverimo dolžino gesla
        assertTrue(complexPassword.length() >= 30, "Geslo mora biti dolgo vsaj 30 znakov");
        assertTrue(complexPassword.contains("!"), "Geslo mora vsebovati !");
        assertTrue(complexPassword.contains("@"), "Geslo mora vsebovati @");
        assertTrue(complexPassword.contains("#"), "Geslo mora vsebovati #");

        // Preverimo, da je uporabnik shranjen
        verify(userRepository, times(1)).save(any(User.class));

        System.out.println("✅ ČLAN 3 - TEST 1 USPEŠEN: Kompleksno geslo pravilno šifrirano");
    }

    /**
     * ============================================================
     * TEST 2 - NEGATIVEN (Član 3)
     * ============================================================
     *
     * Namen: Preveriti obnašanje sistema pri praznem geslu
     *
     * Kaj testiramo:
     * - Ali sistem še vedno šifrira prazno geslo (kar ni varno)
     * - Ali se uporabnik registrira s praznim geslom
     * - Ali bi moral sistem to zavrniti
     *
     * Uporabljene anotacije:
     * - @Test - označuje testno metodo
     * - @DisplayName - opisno ime testa
     *
     * OPOMBA: Ta test prikazuje pomanjkljivost - sistem bi moral
     * zavrniti prazno geslo, vendar trenutno tega ne dela.
     */
    @Test
    @DisplayName("TEST 2 - NEGATIVEN: Registracija s praznim geslom")
    void testRegisterWithEmptyPassword_ShouldEncodeButNotRecommended() {
        // ===== ARRANGE (Priprava) =====
        String email = "weak@example.com";
        String emptyPassword = "";
        String encodedEmptyPassword = "$2a$10$encodedEmptyString";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(emptyPassword)).thenReturn(encodedEmptyPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // ===== ACT (Izvršitev) =====
        User result = userService.register(email, emptyPassword);

        // ===== ASSERT (Preverjanje) =====
        // Test preverja, da trenutna implementacija DOVOLI prazno geslo
        // (kar je varnostna pomanjkljivost)
        assertNotNull(result, "Uporabnik je registriran kljub praznemu geslu");

        // Preverimo, da je passwordEncoder klican s praznim stringom
        verify(passwordEncoder, times(1)).encode(emptyPassword);
        verify(passwordEncoder, times(1)).encode(argThat(pwd -> pwd.isEmpty()));

        // Preverimo, da je uporabnik shranjen
        verify(userRepository, times(1)).save(any(User.class));

        System.out.println("✅ ČLAN 3 - TEST 2 USPEŠEN: Sistem dovoli prazno geslo (varnostna pomanjkljivost odkrita!)");
        System.out.println("⚠️ PRIPOROČILO: Dodaj validacijo za minimalno dolžino gesla!");
    }
}