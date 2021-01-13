package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Livro;

import java.util.ArrayList;

public interface CatalogoListener {
    void onRefreshCatalogoLivros(ArrayList<Livro> catalogoLivros);
    void onRefreshDetalhes();
}
