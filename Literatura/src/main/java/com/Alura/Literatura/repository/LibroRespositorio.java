package com.Alura.Literatura.repository;

import com.Alura.Literatura.model.IdiomasLibros;
import com.Alura.Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRespositorio extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo);

    List<Libro> findByIdioma(IdiomasLibros idioma);
}
