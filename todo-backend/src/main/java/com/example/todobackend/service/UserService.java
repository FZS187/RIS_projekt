package com.example.todobackend.service;

import com.example.todobackend.model.User;
import com.example.todobackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servis za upravljanje uporabnikov
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
     * Registrira novega uporabnika
     *
     * @param email Email uporabnika
     * @param password Geslo v plain text
     * @return Registriran uporabnik
     * @throws IllegalArgumentException če email že obstaja
     */
    /**
     * Registrira novega uporabnika
     *
     * @param email Email uporabnika
     * @param rawPassword Geslo v plain text
     * @return Registriran uporabnik
     * @throws IllegalArgumentException če email že obstaja
     */
    public User register(String email, String rawPassword) {
        // Preverimo, če email že obstaja
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Šifriramo geslo
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // Kreiramo novega uporabnika
        User user = new User(email, hashedPassword, "ROLE_USER");

        // Shranimo v bazo
        return userRepository.save(user);
    }

    /**
     * Najde uporabnika po emailu
     *
     * @param email Email uporabnika
     * @return Optional z uporabnikom ali prazen Optional
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}