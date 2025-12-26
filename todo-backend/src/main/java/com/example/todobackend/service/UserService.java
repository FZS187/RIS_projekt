package com.example.todobackend.service;

import com.example.todobackend.model.User;
import com.example.todobackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servis za upravljanje korisnicima
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registruje novog korisnika
     *
     * @param name Ime korisnika
     * @param email Email korisnika
     * @param rawPassword Lozinka u plain text
     * @return Registrovan korisnik
     * @throws IllegalArgumentException Ako email već postoji
     */
    public User register(String name, String email, String rawPassword) {
        // Proveri da li email već postoji
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Šifriraj lozinku
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // Kreiraj novog korisnika
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole("ROLE_USER");

        // Sačuvaj u bazu
        return userRepository.save(user);
    }

    /**
     * Pronađi korisnika po email-u
     *
     * @param email Email korisnika
     * @return Optional sa korisnikom ili prazan Optional
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Proveri da li korisnik sa datim email-om postoji
     *
     * @param email Email za proveru
     * @return true ako postoji, false ako ne postoji
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}