package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Livro;
import java.util.ArrayList;

public interface FavoritoListener {
    void onRefreshFavoritoLivros(ArrayList<Livro> favoritoLivros);

    void onRefreshDetalhes();
}
