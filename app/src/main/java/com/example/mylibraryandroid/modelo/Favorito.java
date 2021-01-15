package com.example.mylibraryandroid.modelo;

public class Favorito {
    private int id_favorito, id_livro, id_utilizador;
    private String dta_favorito;

    public Favorito(int id_favorito, int id_livro, int id_utilizador, String dta_favorito) {
        this.id_favorito = id_favorito;
        this.id_livro = id_livro;
        this.id_utilizador = id_utilizador;
        this.dta_favorito = dta_favorito;
    }

    public int getId_favorito() {
        return id_favorito;
    }

    public int getId_livro() {
        return id_livro;
    }

    public int getId_utilizador() {
        return id_utilizador;
    }

    public String getDta_favorito() {
        return dta_favorito;
    }

    public void setId_favorito(int id_favorito) {
        this.id_favorito = id_favorito;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public void setId_utilizador(int id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public void setDta_favorito(String dta_favorito) {
        this.dta_favorito = dta_favorito;
    }

}
