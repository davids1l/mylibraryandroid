package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Livro;

import java.util.ArrayList;

public interface LivrosDetalhesReqListener {
    void onRefreshDetalhesReq(ArrayList<Livro> livrosDetalhesRequisicao);
}
