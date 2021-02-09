package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.RequisicaoLivro;

import java.util.ArrayList;

public interface RequisicaoLivroListener {
    void onRefreshRequisicaoLivro(ArrayList<RequisicaoLivro> requisicoesLivros);
}
