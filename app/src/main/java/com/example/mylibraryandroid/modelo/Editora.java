package com.example.mylibraryandroid.modelo;

public class Editora {

    int id_editora, id_pais;
    String designacao;

    public Editora(int id_editora, String designacao, int id_pais) {
        this.id_editora = id_editora;
        this.id_pais = id_pais;
        this.designacao = designacao;
    }

    public int getId_editora() {
        return id_editora;
    }

    public int getId_pais() {
        return id_pais;
    }

    public String getDesignacao() {
        return designacao;
    }
}
