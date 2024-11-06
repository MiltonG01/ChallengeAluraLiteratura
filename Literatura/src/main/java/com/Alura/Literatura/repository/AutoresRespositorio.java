package com.Alura.Literatura.repository;

import com.Alura.Literatura.model.Autor;
import com.Alura.Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutoresRespositorio extends JpaRepository<Autor, Long>  {
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND a.fechaDeMuerte >= :anio")
    List<Autor> autoresVivosEnUnDeterminadoAnio(@Param("anio") Integer anio);
}
