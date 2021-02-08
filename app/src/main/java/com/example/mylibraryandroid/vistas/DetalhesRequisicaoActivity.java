package com.example.mylibraryandroid.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
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

        fragmentManager = getSupportFragmentManager();

        int id_requisicao = getIntent().getIntExtra(ID_REQUISICAO, -1);
        requisicao = Singleton.getInstance(this).getRequisicao(id_requisicao);

        tvNumReq = findViewById(R.id.tvNumReq);
        tvEstadoReq = findViewById(R.id.tvEstadoReq);
        tvDataLevantamento = findViewById(R.id.tvDataLevantamento);
        tvDataEntrega = findViewById(R.id.tvDataEntrega);
        tvBibLevantamento = findViewById(R.id.tvBibLevantamento);

        Singleton.getInstance(getApplicationContext()).setRequisicaoListener(this);

        if(requisicao!=null){
            setTitle("Requisição #"+requisicao.getId_requisicao());
            popularDetalhesRequisicao();
        }
    }

    public void popularDetalhesRequisicao(){
        nomeBib = Singleton.getInstance(getApplicationContext()).getNomeBiblioteca(requisicao.getId_bib_levantamento());

        tvNumReq.setText("# "+requisicao.getId_requisicao());
        tvEstadoReq.setText(requisicao.getEstado());
        tvDataLevantamento.setText(requisicao.getDta_levantamento());
        tvDataEntrega.setText(requisicao.getDta_entrega());
        tvBibLevantamento.setText(nomeBib);
    }

    @Override
    public void onRefreshRequisicao(ArrayList<Requisicao> requisicoes) {

    }
}