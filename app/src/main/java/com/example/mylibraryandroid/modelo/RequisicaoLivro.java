package com.example.mylibraryandroid.modelo;

public class RequisicaoLivro {
    private int id_livro, id_requisicao;

    public RequisicaoLivro(int id_livro, int id_requisicao) {
        this.id_livro = id_livro;
        this.id_requisicao = id_requisicao;
    }

    public int getId_livro() {
        return id_livro;
    }

    public int getId_requisicao() {
        return id_requisicao;
    }
}
