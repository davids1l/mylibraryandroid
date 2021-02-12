package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Requisicao;

import java.util.ArrayList;

public interface RequisicaoListener {
    void onRefreshRequisicao(ArrayList<Requisicao> requisicoes);
    void onRefreshRequisicoes();
}
