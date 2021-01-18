package com.example.mylibraryandroid.listeners;

import com.example.mylibraryandroid.modelo.Comentario;

import java.util.ArrayList;

public interface ComentarioListener {
    void onRefreshComentarios(ArrayList<Comentario> comentarios);

}
