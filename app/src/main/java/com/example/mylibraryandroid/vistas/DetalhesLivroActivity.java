package com.example.mylibraryandroid.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class DetalhesLivroActivity extends AppCompatActivity implements CatalogoListener {


    public static final String ID_LIVRO = "ID_LIVRO";
    private Livro livro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);

        //seta de go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id_livro = getIntent().getIntExtra(ID_LIVRO, -1);
        livro = Singleton.getInstance(this).getLivro(id_livro);

    }

    @Override
    public void onRefreshCatalogoLivros(ArrayList<Livro> catalogo) {

    }

    @Override
    public void onRefreshDetalhes() {
        setResult(RESULT_OK);
        finish();
    }
}