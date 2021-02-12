package com.example.mylibraryandroid.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.LivroJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DetalhesLivroActivity extends AppCompatActivity implements CatalogoListener {


    public static final String ID_LIVRO = "ID_LIVRO";
    private Livro livro;
    private TextView tvTitulo, tvAutor, tvEdicao, tvPaginas, tvBiblioteca, tvSinopse, tvIsbn, tvGenero, tvIdioma, tvEditora, tvFormato;
    private ImageView imgCapa;
    StringBuilder stringBuilder = new StringBuilder();
    private String nomeAutor;
    private String desigEditora;
    private String nomeBib;

    private int id_livro;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);

        //seta de go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(MenuMainActivity.ID, "");

        id_livro = getIntent().getIntExtra(ID_LIVRO, -1);
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
        tvFormato = findViewById(R.id.tvFormato);
        imgCapa = findViewById(R.id.imgCapa);

        Singleton.getInstance(getApplicationContext()).setCatalogoListener(this);

        LinearLayout layoutComentarios = (LinearLayout) findViewById(R.id.ll_comentarios);
        layoutComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarFragmentoComentario();
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(LivroJsonParser.isConnectionInternet(getApplicationContext())) {

                    //obter o id_utilizador armazenado na shared preferences
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                    String id_utilizador = sharedPreferences.getString(MenuMainActivity.ID,"");

                    //request à API pelo total de livros em requisição
                    Singleton.getInstance(getApplicationContext()).totalEmRequisicao(getApplicationContext(), Integer.parseInt(id_utilizador));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //adicionar livro ao carrinho
                    //verifica se o livro em questão esta em requisição, limita o total de livros no carrinho e verifica se são ambíguos
                    Singleton.getInstance(getApplicationContext()).verificarEmRequisicao(getApplicationContext(), id_livro);

                } else {
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(livro!=null){
            setTitle(livro.getTitulo());
            popularDetalhesLivro();
        }

    }

    private void carregarFragmentoComentario() {
        ComentarioLivrosFragment comentarioLivrosFragment = new ComentarioLivrosFragment();
        Toast.makeText(getApplicationContext(), "im here yoo", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .add(R.id.comentarios_livro, comentarioLivrosFragment, comentarioLivrosFragment.getTag())
            .setReorderingAllowed(true)
            .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(livro != null){
            // MenuItem item = menu.findItem(R.id.bedSwitch);
            int id_utilizador = Integer.parseInt(id);
            String favId = Singleton.getInstance(getApplicationContext()).findFavoritoByIDS(id_livro, id_utilizador);
            if(favId != null) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu_favorito_livro, menu);
                MenuItem item = menu.findItem(R.id.itemFavorito);
                item.setIcon(R.drawable.ic_favorito);
                item.setEnabled(false);
                return super.onCreateOptionsMenu(menu);
            } else {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu_favorito_livro, menu);
                return super.onCreateOptionsMenu(menu);
            }
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(LivroJsonParser.isConnectionInternet(getApplicationContext())) {
            SharedPreferences sharedPreferences = this.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
            String token = sharedPreferences.getString(MenuMainActivity.TOKEN,"");
            switch (item.getItemId()) {
                case R.id.itemFavorito:
                    int id_utilizador = Integer.parseInt(id);
                    Singleton.getInstance(getApplicationContext()).adicionarFavoritoAPI(getApplicationContext(), id_utilizador, id_livro, token);
                    item.setIcon(R.drawable.ic_favorito);
                    item.setEnabled(false);
                    return true;
                case android.R.id.home:
                    finish();
                    return true;
            }
        }else {
            Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void popularDetalhesLivro(){
        nomeAutor = Singleton.getInstance(getApplicationContext()).getNomeAutor(livro.getId_autor());
        desigEditora = Singleton.getInstance(getApplicationContext()).getDesignacaoEditora(livro.getId_editora());
        nomeBib = Singleton.getInstance(getApplicationContext()).getNomeBiblioteca(livro.getId_biblioteca());

        tvTitulo.setText(livro.getTitulo());
        tvAutor.setText(nomeAutor);
        tvIsbn.setText(livro.getIsbn()+"");
        tvEdicao.setText(livro.getAno()+"");
        tvGenero.setText(livro.getGenero());
        tvIdioma.setText(livro.getIdioma());
        tvSinopse.setText(livro.getSinopse());
        tvFormato.setText(livro.getFormato());
        tvBiblioteca.setText(nomeBib);
        tvPaginas.setText(livro.getPaginas()+"");
        tvEditora.setText(desigEditora);

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