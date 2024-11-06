package com.Alura.Literatura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeMuerte;


    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(DatosAutor datosAuthor) {
        this.nombre = datosAuthor.nombre();
        this.fechaDeNacimiento = datosAuthor.fechaDeNacimiento();
        this.fechaDeMuerte = datosAuthor.fechaDeMuerte();
    }

    @Override
    public String toString() {
        String librosNombres = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", "));

        return "-------------Autor-------------\n" +
                " Nombre: '" + nombre + "\n" +
                " Fecha De Nacimiento: " + fechaDeNacimiento +"\n"+
                " FechaDeMuerte: " + fechaDeMuerte +"\n"+
                " Libros: " + librosNombres+"\n"
                ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(Integer fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libro> getLibros() {

        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


}
