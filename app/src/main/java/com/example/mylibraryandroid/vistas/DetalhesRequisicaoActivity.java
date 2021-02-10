package com.example.mylibraryandroid.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.RequisicaoListener;
import com.example.mylibraryandroid.modelo.Requisicao;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class DetalhesRequisicaoActivity extends AppCompatActivity implements RequisicaoListener {

    public static final String ID_REQUISICAO = "ID_REQUISICAO";

    private TextView tvNumReq, tvEstadoReq, tvBibLevantamento, tvDataLevantamento, tvDataEntrega;
    private String nomeBib;
    private Requisicao requisicao;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_requisicao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = this.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String tokenLeitor = sharedPreferences.getString(MenuMainActivity.TOKEN, "");
        String idLeitor = sharedPreferences.getString(MenuMainActivity.ID, "");

        int id_requisicao = getIntent().getIntExtra(ID_REQUISICAO, -1);
        requisicao = Singleton.getInstance(this).getRequisicao(id_requisicao);


        //Singleton.getInstance(this).getRequisicaoLivrosAPI(this, tokenLeitor, String.valueOf(id_requisicao));

        LivrosRequisicaoFragment requisicaoLivrosFragment = new LivrosRequisicaoFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace((R.id.fragment_livros_req), requisicaoLivrosFragment, requisicaoLivrosFragment.getTag())
                .commit();


        tvNumReq = findViewById(R.id.tvNumReq);
        tvEstadoReq = findViewById(R.id.tvEstadoReq);
        tvDataLevantamento = findViewById(R.id.tvDataLevantamento);
        tvDataEntrega = findViewById(R.id.tvDataEntrega);
        tvBibLevantamento = findViewById(R.id.tvBibLevantamento);

        //Singleton.getInstance(getApplicationContext()).setRequisicaoListener(this);


        if(requisicao!=null){
            setTitle("Requisição #"+requisicao.getId_requisicao());
            popularDetalhesRequisicao();
        }
    }

    public void popularDetalhesRequisicao(){
        nomeBib = Singleton.getInstance(getApplicationContext()).getNomeBiblioteca(requisicao.getId_bib_levantamento());

        tvNumReq.setText("# "+requisicao.getId_requisicao());
        tvEstadoReq.setText(requisicao.getEstado());
        if(requisicao.getDta_levantamento() == null && requisicao.getDta_entrega() == null){
            tvDataLevantamento.setText(requisicao.getDta_levantamento());
            tvDataEntrega.setText(requisicao.getDta_entrega());
        } else {
            tvDataLevantamento.setText("Sem informação.");
            tvDataEntrega.setText("Sem informação.");
        }
        tvBibLevantamento.setText(nomeBib);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        finish();
        return true;
    }

    @Override
    public void onRefreshRequisicao(ArrayList<Requisicao> requisicoes) {

    }
}