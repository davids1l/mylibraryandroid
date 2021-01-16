package com.example.mylibraryandroid.modelo;

public class Livro {

    private int id_livro, ano, paginas, id_editora, id_biblioteca, id_autor, isbn;
    private String titulo, genero, idioma, formato, capa, sinopse;
    private static final String IP = "http://192.168.0.102";
    private String urlCapas = IP + ":8888/web/imgs/capas/";

    public Livro(int id_livro, String titulo, int isbn, int ano, int paginas, String genero, String idioma, String formato, String capa, String sinopse, int id_editora, int id_biblioteca, int id_autor) {
        this.id_livro = id_livro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.ano = ano;
        this.paginas = paginas;
        this.genero = genero;
        this.idioma = idioma;
        this.formato = formato;
        this.capa = urlCapas + capa;
        this.sinopse = sinopse;
        this.id_editora = id_editora;
        this.id_biblioteca = id_biblioteca;
        this.id_autor = id_autor;
    }


    public int getId_livro() {
        return id_livro;
    }

    public int getAno() {
        return ano;
    }

    public int getPaginas() {
        return paginas;
    }

    public int getId_editora() {
        return id_editora;
    }

    public int getId_biblioteca() {
        return id_biblioteca;
    }

    public int getId_autor() {
        return id_autor;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getFormato() {
        return formato;
    }

    public String getCapa() {
        return capa;
    }

    public String getSinopse() {
        return sinopse;
    }
}
