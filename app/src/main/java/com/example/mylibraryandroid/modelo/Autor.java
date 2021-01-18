package com.example.mylibraryandroid.modelo;

public class Autor {

    int id_autor, id_pais;
    String nome_autor;

    public Autor(int id_autor, String nome_autor, int id_pais) {
        this.id_autor = id_autor;
        this.id_pais = id_pais;
        this.nome_autor = nome_autor;
    }

    public int getId_autor() {
        return id_autor;
    }

    public int getId_pais() {
        return id_pais;
    }

    public String getNome_autor() {
        return nome_autor;
    }

    @Override
    public String toString() {
        return nome_autor;
    }
}
