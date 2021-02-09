package com.example.mylibraryandroid.modelo;

public class Requisicao {

    private int id_requisicao, id_utilizador, id_bib_levantamento;
    private String dta_levantamento, dta_entrega, estado;

    public Requisicao(int id_requisicao, String dta_levantamento, String dta_entrega, String estado, int id_utilizador, int id_bib_levantamento) {
        this.id_requisicao = id_requisicao;
        this.dta_levantamento = dta_levantamento;
        this.dta_entrega = dta_entrega;
        this.estado = estado;
        this.id_utilizador = id_utilizador;
        this.id_bib_levantamento = id_bib_levantamento;
    }

    public int getId_requisicao() {
        return id_requisicao;
    }

    public int getId_utilizador() {
        return id_utilizador;
    }

    public int getId_bib_levantamento() {
        return id_bib_levantamento;
    }

    public String getDta_levantamento() {
        return dta_levantamento;
    }

    public String getDta_entrega() {
        return dta_entrega;
    }

    public String getEstado() {
        return estado;
    }
}
