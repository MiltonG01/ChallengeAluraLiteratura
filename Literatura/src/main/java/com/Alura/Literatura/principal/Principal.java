package com.Alura.Literatura.principal;

import com.Alura.Literatura.model.*;
import com.Alura.Literatura.repository.AutoresRespositorio;
import com.Alura.Literatura.repository.LibroRespositorio;
import com.Alura.Literatura.service.ConsumoAPI;
import com.Alura.Literatura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/" ;
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutoresRespositorio autoresRespositorio;
    private LibroRespositorio libroRepositorio;

    public Principal(AutoresRespositorio autor, LibroRespositorio libro) {
        this.autoresRespositorio = autor;
        this.libroRepositorio = libro;
    }


    public void muestraMenu(){
        var json = consumoApi.obtenerDatos(URL_BASE);

            int opcion = -1;
            while (opcion != 0) {
                String menu = """
                    ------------------------------
                   1.- Buscar Libro por titulo
                   2.- Mostrar los libros registrados
                   3.- Mostrar los autores
                   4.- Buscar autores que se encontraban vivos en determinado año
                   5.- Mostrar libros por idioma
                   
                                        
                    0.-Salir del Programa
                   """;

                System.out.println( "\n"+ menu);
                System.out.print("Opción: ");
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibrosPorNombre();
                        break;
                    case 2:
                        mostrarLosLibrosRegistrados();
                        break;
                    case 3:
                        listaAutoresRegistrados();
                        break;
                    case 4:
                        AutoresVivosEnDeterminadoTiempo();
                        break;
                    case 5:
                        listarLibrosPorIdiomas();
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("\nOpción no válida\n");
                        break;
                }
            }
    }

    private void buscarLibrosPorNombre() {
        System.out.println("Ingrese nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();


        String url = URL_BASE + "?search=" + tituloLibro.replace(" ", "+");
        String json = consumoApi.obtenerDatos(url);
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados()
                .stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibros datoslibroEcontrado = libroBuscado.get();

            DatosAutor datosAutor = datoslibroEcontrado.autor().get(0);

            Autor autor = autoresRespositorio.findByNombreIgnoreCase(datosAutor.nombre()).orElseGet(() -> {
                Autor nuevoAutor = new Autor(datosAutor);
                return autoresRespositorio.save(nuevoAutor);
            });


            Optional<Libro> libroExiste = libroRepositorio.findByTituloIgnoreCase(datoslibroEcontrado.titulo());

            if (libroExiste.isPresent()) {
                System.out.println("\nEl libro ya esta registrado, pruebe con otro libro\n");
            } else {

                Libro libroEcontrado = new Libro(datoslibroEcontrado);
                libroEcontrado.setAutor(autor);
                libroRepositorio.save(libroEcontrado);
                System.out.println(libroEcontrado);
                System.out.println("\nLibro registrado exitosamente\n");
            }
        } else {
            System.out.println("\nLibro no encontrado, pruebe con otro libro\n");
        };
    }
    private void mostrarLosLibrosRegistrados() {
        List<Libro> libros = libroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados");
        } else {
            libros.forEach(System.out::println);
        }
    }
    private void listaAutoresRegistrados() {
        List<Autor> autores = autoresRespositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores registrados");
        } else {
            autores.forEach(System.out::println);
        }
    }
    private void AutoresVivosEnDeterminadoTiempo() {
        System.out.println("Ingrese el año en el cual desea buscar autores vivos");

        try {
            int anio = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autoresVivos = autoresRespositorio.autoresVivosEnUnDeterminadoAnio(anio);
            if (autoresVivos.isEmpty()) {
                System.out.println("\nNo hay autores vivos en el año " + anio);
            } else {
                autoresVivos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("\nAño no válido");
            teclado.nextLine();
        }

    }

    private void listarLibrosPorIdiomas() {
        System.out.println("Ingrese el idioma en el cual desea buscar los libros");
        int opcion = -1;
        while (opcion != 0) {
            String menuIdiomas = """
                    1 - Español
                    2 - Ingles
                    3 - Frances
                    4 - Portugues
                    0 - Salir
                    """;
            System.out.println(menuIdiomas);

            try {
                opcion = teclado.nextInt();
            } catch (Exception e) {
                System.out.println("Opción no válida");
                teclado.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    List<Libro> librosEspanol = libroRepositorio.findByIdioma(IdiomasLibros.ES);
                    if (librosEspanol.isEmpty()) {
                        System.out.println("\nNo hay libros en español");
                    } else {
                        librosEspanol.forEach(System.out::println);
                    }
                    break;
                case 2:
                    List<Libro> librosIngles = libroRepositorio.findByIdioma(IdiomasLibros.EN);
                    if (librosIngles.isEmpty()) {
                        System.out.println("\nNo hay libros en inglés");
                    } else {
                        librosIngles.forEach(System.out::println);
                    }
                    break;
                case 3:
                    List<Libro> librosFrances = libroRepositorio.findByIdioma(IdiomasLibros.FR);
                    if (librosFrances.isEmpty()) {
                        System.out.println("\nNo hay libros en francés");
                    } else {
                        librosFrances.forEach(System.out::println);
                    }
                    break;
                case 4:
                    List<Libro> librosPortugues = libroRepositorio.findByIdioma(IdiomasLibros.PT);
                    if (librosPortugues.isEmpty()) {
                        System.out.println("\nNo hay libros en portugués");
                    } else {
                        librosPortugues.forEach(System.out::println);
                    }
                    break;
                default:
                    System.out.println("\nOpción no válida");
                    break;
            }

        }

    }

}


