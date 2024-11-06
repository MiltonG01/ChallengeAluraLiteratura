package com.Alura.Literatura.model;

public enum IdiomasLibros {
    ES("[es]", "Español"),
    EN("[en]", "Inglés"),
    FR("[fr]", "Francés"),
    PT("[pt]", "Portugués");

    private final String idiomaApi;
    private final String idioma;

    IdiomasLibros(String idiomaApi, String idioma) {
        this.idiomaApi = idiomaApi;
        this.idioma = idioma;
    }

    public static IdiomasLibros fromString(String idioma) {
        for (IdiomasLibros i : IdiomasLibros.values()) {
            if (i.idiomaApi.equalsIgnoreCase(idioma)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + idioma);
    }

    public static IdiomasLibros fromEspanol(String idioma) {
        for (IdiomasLibros i : IdiomasLibros.values()) {
            if (i.idioma.equals(idioma)) {
                return i;
            }
        }
        return null;
    }
}
