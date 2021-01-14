package com.example.mylibraryandroid.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.LivroJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetalhesLivroActivity extends AppCompatActivity implements CatalogoListener {


    public static final String ID_LIVRO = "ID_LIVRO";
    private Livro livro;
    private TextView tvTitulo, tvAutor, tvEdicao, tvPaginas, tvBiblioteca, tvSinopse, tvIsbn, tvGenero, tvIdioma, tvEditora;
    private ImageView imgCapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);

        //seta de go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id_livro = getIntent().getIntExtra(ID_LIVRO, -1);
        livro = Singleton.getInstance(this).getLivro(id_livro);

        tvTitulo = findViewById(R.id.tvTitulo);
        tvAutor = findViewById(R.id.tvAutor);
        tvIsbn = findViewById(R.id.tvIsbn);
        tvEdicao = findViewById(R.id.tvEdicao);
        tvGenero = findViewById(R.id.tvGenero);
        tvIdioma = findViewById(R.id.tvIdioma);
        tvSinopse = findViewById(R.id.tvSinopse);
        tvBiblioteca = findViewById(R.id.tvBiblioteca);
        tvEditora = findViewById(R.id.tvEditora);
        tvPaginas = findViewById(R.id.tvPaginas);
        imgCapa = findViewById(R.id.imgCapa);

        Singleton.getInstance(getApplicationContext()).setCatalogoListener(this);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //if(LivroJsonParser)
            }
        });

        if(livro!=null){
            setTitle(livro.getTitulo());
            popularDetalhesLivro();
        }

    }

    public void popularDetalhesLivro(){
        tvTitulo.setText(livro.getTitulo());
        tvAutor.setText(livro.getId_autor()+"");
        tvIsbn.setText(livro.getIsbn()+"");
        tvEdicao.setText(livro.getAno()+"");
        tvGenero.setText(livro.getGenero());
        tvIdioma.setText(livro.getIdioma());
        tvSinopse.setText(livro.getSinopse());
        tvBiblioteca.setText(livro.getId_biblioteca()+"");
        tvPaginas.setText(livro.getPaginas()+"");
        tvEditora.setText(livro.getId_editora()+"");

        Glide.with(this)
                .load(livro.getCapa())
                .placeholder(R.drawable.loading_capa)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);

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