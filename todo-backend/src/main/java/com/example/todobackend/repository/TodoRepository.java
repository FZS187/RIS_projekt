package com.example.todobackend.repository;

import com.example.todobackend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // NOVO: Metoda za pretragu
    // Spring automatski generiše SQL upit koji pronalazi sve zadatke
    // čije 'name' polje sadrži (LIKE %keyword%) datu ključnu reč (ignorišući velika/mala slova)
    List<Todo> findByNameContainingIgnoreCase(String keyword);

    // NOVO: Metoda za filtriranje po statusu (completed)
    List<Todo> findByCompleted(boolean completed);

    // NOVO: Metoda za kombinovano filtriranje
    List<Todo> findByNameContainingIgnoreCaseAndCompleted(String keyword, boolean completed);
}