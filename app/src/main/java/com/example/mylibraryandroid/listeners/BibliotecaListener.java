package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Biblioteca;

import java.util.ArrayList;

public interface BibliotecaListener {

    void onRefreshBibliotecas(ArrayList<Biblioteca> bibliotecas);

}
