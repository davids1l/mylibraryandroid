package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Livro;

import java.util.ArrayList;

public interface CarrinhoListener {

    void onRefreshCarrinhoLivros(ArrayList<Livro> carrinho);

    void onRefreshDetalhes();
}
