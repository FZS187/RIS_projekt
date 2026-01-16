package com.example.todobackend.controller;

import com.example.todobackend.model.User;
import com.example.todobackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = {"http://localhost:5173", "http://127.0.0.1:5173"},
        allowCredentials = "true"
)
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * ✅ REGISTRACIJA - Automatski uloguje korisnika nakon registracije
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        try {
            System.out.println("📝 Registracija započeta za: " + request.email());

            // ✅ Registruj korisnika
            User user = userService.register(request.name(), request.email(), request.password());

            System.out.println("✅ Korisnik registrovan: " + user.getEmail());

            // ✅ AUTOMATSKI ULOGUJ nakon registracije
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            // Postavi Security Context
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            // Sačuvaj u sesiju
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

            System.out.println("✅ Korisnik automatski ulogovan: " + user.getEmail());

            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "name", user.getName() != null ? user.getName() : user.getEmail(),
                    "message", "Registered and logged in successfully"
            ));

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Registracija neuspešna: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            System.err.println("❌ Neočekivana greška: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    /**
     * ✅ LOGIN
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        try {
            System.out.println("🔐 Login pokušaj za: " + request.email());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

            // Dohvati korisnika iz baze
            User user = userService.findByEmail(request.email())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println("✅ Login uspešan za: " + user.getEmail());

            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "name", user.getName() != null ? user.getName() : user.getEmail(),
                    "message", "Logged in successfully"
            ));

        } catch (Exception e) {
            System.err.println("❌ Login neuspešan: " + e.getMessage());
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    /**
     * ✅ LOGOUT
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        System.out.println("🚪 Korisnik se odjavio");

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    /**
     * ✅ Request DTO
     */
    public record AuthRequest(String name, String email, String password) {}
}