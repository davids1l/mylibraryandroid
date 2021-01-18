package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Utilizador;

public interface PerfilListener {
    void onRefreshUtilizador(Utilizador utilizador);
    void onRefreshEmailUtilizador(String email);
}
