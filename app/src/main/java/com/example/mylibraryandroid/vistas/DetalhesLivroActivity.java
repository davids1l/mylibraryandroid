package com.example.mylibraryandroid.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

public class DetalhesLivroActivity extends AppCompatActivity implements CatalogoListener {


    public static final String ID_LIVRO = "ID_LIVRO";
    private Livro livro;
    private ArrayList<Livro> carrinho;
    private TextView tvTitulo, tvAutor, tvEdicao, tvPaginas, tvBiblioteca, tvSinopse, tvIsbn, tvGenero, tvIdioma, tvEditora;
    private ImageView imgCapa;
    StringBuilder stringBuilder = new StringBuilder();

    private int id_livro;
    private String id;
    private FragmentManager fragmentManager;

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
        imgCapa = findViewById(R.id.imgCapa);

        Singleton.getInstance(getApplicationContext()).setCatalogoListener(this);

        LinearLayout layoutComentarios = (LinearLayout) findViewById(R.id.ll_comentarios);
        layoutComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Teste :)", Toast.LENGTH_SHORT).show();
                carregarFragmentoComentario();
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(LivroJsonParser.isConnectionInternet(getApplicationContext())) {

                    //Singleton.getInstance(getApplicationContext()).adicionarCarrinho(id_livro);

                    if (Singleton.getInstance(getApplicationContext()).adicionarCarrinho(id_livro) == true){
                        /*carrinho = Singleton.getInstance(getApplicationContext()).getLivrosCarrinho();
                        for (int i=0; i<carrinho.size(); i++){
                            //stringBuilder.append(carrinho.get(i) + "\n");
                            //carrinho.get(i);
                            stringBuilder.append(carrinho.get(i).getTitulo() + "\n");
                        }
                        tvPaginas.setText(stringBuilder);*/
                        Toast.makeText(getApplicationContext(), "Livro adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "O livro já se encontra no seu carrinho", Toast.LENGTH_SHORT).show();
                    }
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
        Fragment fragment = new ComentarioLivrosFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
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
            switch (item.getItemId()) {
                case R.id.itemFavorito:
                    int id_utilizador = Integer.parseInt(id);
                    Singleton.getInstance(getApplicationContext()).adicionarFavoritoAPI(getApplicationContext(), id_utilizador, id_livro);
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