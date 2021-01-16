package com.example.mylibraryandroid.modelo;

public class Biblioteca {

    int id_biblioteca;
    String nome, cod_postal;

    public Biblioteca(int id_biblioteca, String nome, String cod_postal){
        this.id_biblioteca = id_biblioteca;
        this.nome = nome;
        this.cod_postal = cod_postal;
    }

    public int getId_biblioteca() {
        return id_biblioteca;
    }

    public String getNome() {
        return nome;
    }

    public String getCod_postal() {
        return cod_postal;
    }
}
