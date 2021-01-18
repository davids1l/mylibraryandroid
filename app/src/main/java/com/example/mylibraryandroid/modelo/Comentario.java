package com.example.mylibraryandroid.modelo;

public class Comentario {
    private int id_comentario, id_livro, id_utilizador;
    private String comentario, dta_comentario;

    public Comentario(int id_comentario, int id_livro, int id_utilizador, String comentario, String dta_comentario) {
        this.id_comentario = id_comentario;
        this.id_livro = id_livro;
        this.id_utilizador = id_utilizador;
        this.comentario = comentario;
        this.dta_comentario = dta_comentario;
    }

    public int getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(int id_comentario) {
        this.id_comentario = id_comentario;
    }

    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public int getId_utilizador() {
        return id_utilizador;
    }

    public void setId_utilizador(int id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDta_comentario() {
        return dta_comentario;
    }

    public void setDta_comentario(String dta_comentario) {
        this.dta_comentario = dta_comentario;
    }
}
