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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit testi za UserService
 *
 * Avtor: Boris Sajlović
 * Datum: 12.12.2025
 *
 * Testira: REGISTRACIJO uporabnikov
 * - Pozitiven test: Uspešna registracija
 * - Negativen test: Duplikat email
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests - Član 1: Registracija")
class UserServiceTest {

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
     * Namen: Testiranje uspešne registracije novega uporabnika
     *
     * Funkcionalnost: Registracija uporabnika
     *
     * Scenarij:
     * 1. Nov uporabnik se poskuša registrirati z veljavnim emailom
     * 2. Email še ne obstaja v bazi
     * 3. Geslo se šifrira
     * 4. Uporabnik se shrani v bazo
     *
     * Pričakovan rezultat:
     * - Uporabnik je uspešno registriran
     * - Geslo je šifrirano
     * - Repository metode so klicane pravilno
     */
    @Test
    @DisplayName("POZITIVEN: Uspešna registracija novega uporabnika")
    void testRegisterNewUser_Success() {
        // ARRANGE - Priprava podatkov
        String email = "newuser@example.com";
        String rawPassword = "securePassword123";
        String encodedPassword = "encodedPassword123";

        // Mock obnašanje
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // ACT - Izvršitev
        User result = userService.register(email, rawPassword);

        // ASSERT - Preverjanje
        assertNotNull(result, "Registriran uporabnik ne sme biti null");

        // Preveri, da je passwordEncoder.encode() klican točno 1x
        verify(passwordEncoder, times(1)).encode(rawPassword);

        // Preveri, da je userRepository.save() klican točno 1x
        verify(userRepository, times(1)).save(any(User.class));

        // Preveri, da je existsByEmail() klican točno 1x
        verify(userRepository, times(1)).existsByEmail(email);

        System.out.println("✅ ČLAN 1 - POZITIVEN TEST: Uporabnik uspešno registriran");
    }

    /**
     * TEST 2 - NEGATIVEN
     *
     * Namen: Testiranje zavritve registracije z že obstoječim emailom
     *
     * Funkcionalnost: Preprečevanje dupliciranih emailov
     *
     * Scenarij:
     * 1. Uporabnik se poskuša registrirati z emailom
     * 2. Email že obstaja v bazi
     * 3. Sistem zavrne registracijo
     * 4. Vrže IllegalArgumentException
     *
     * Pričakovan rezultat:
     * - Metoda vrže IllegalArgumentException
     * - Sporočilo izjeme je "Email already exists"
     * - Uporabnik NI shranjen v bazo
     * - Geslo NI šifrirano
     */
    @Test
    @DisplayName("NEGATIVEN: Registracija z obstoječim emailom vrže izjemo")
    void testRegisterExistingEmail_ThrowsException() {
        // ARRANGE - Priprava podatkov
        String existingEmail = "existing@example.com";
        String password = "somePassword123";

        // Mock obnašanje - email že obstaja
        when(userRepository.existsByEmail(existingEmail)).thenReturn(true);

        // ACT & ASSERT - Izvršitev in preverjanje
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register(existingEmail, password),
                "Metoda bi morala vreči IllegalArgumentException"
        );

        // Preveri sporočilo izjeme
        assertEquals("Email already exists", exception.getMessage(),
                "Sporočilo izjeme se ne ujema");

        // Preveri, da save() NIKOLI ni bil klican
        verify(userRepository, never()).save(any(User.class));

        // Preveri, da encode() NIKOLI ni bil klican
        verify(passwordEncoder, never()).encode(anyString());

        // Preveri, da je existsByEmail() klican točno 1x
        verify(userRepository, times(1)).existsByEmail(existingEmail);

        System.out.println("Boris Sajlovic - NEGATIVEN TEST: Duplikat email pravilno zavrnjen");
    }
}