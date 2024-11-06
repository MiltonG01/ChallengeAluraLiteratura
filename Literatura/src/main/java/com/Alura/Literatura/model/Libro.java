package com.Alura.Literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.idioma = IdiomasLibros.fromString(datosLibros.idiomas().toString().split(",")[0]);
        this.numeroDescargas = datosLibros.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return "\n____________________LIBRO____________________\n" +

                " Titulo: '" + titulo + "\n" +
                " Autor: " + autor.getNombre() +"\n"+
                " Idioma: " + idioma +"\n"+
                " Numero Descargas: " + numeroDescargas +"\n"+
                "_____________________________________________\n"
                ;
    }

    @Enumerated(EnumType.STRING)
    private IdiomasLibros idioma;
    private double numeroDescargas;

    @ManyToOne
    private Autor autor;

    public Libro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public IdiomasLibros getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomasLibros idioma) {
        this.idioma = idioma;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
