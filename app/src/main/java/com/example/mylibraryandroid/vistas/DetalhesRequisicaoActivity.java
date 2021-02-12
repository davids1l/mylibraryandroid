package com.example.mylibraryandroid.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.RequisicaoListener;
import com.example.mylibraryandroid.modelo.Requisicao;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.JsonParser;
import com.example.mylibraryandroid.utils.LivroJsonParser;

import java.util.ArrayList;

public class DetalhesRequisicaoActivity extends AppCompatActivity {

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


        int id_requisicao = getIntent().getIntExtra(ID_REQUISICAO, -1);
        requisicao = Singleton.getInstance(this).getRequisicao(id_requisicao);


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


        if(requisicao!=null){
            setTitle("Requisição #"+requisicao.getId_requisicao());
            popularDetalhesRequisicao();
        }
    }

    public void popularDetalhesRequisicao(){
        nomeBib = Singleton.getInstance(getApplicationContext()).getNomeBiblioteca(requisicao.getId_bib_levantamento());

        //tvNumReq.setText("# "+requisicao.getId_requisicao());
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
        if(LivroJsonParser.isConnectionInternet(getApplicationContext())){
            if (requisicao.getEstado().equals("A aguardar tratamento") || requisicao.getEstado().equals("Pronta a levantar")){
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu_cancelar_requisicao, menu);
                MenuItem item = menu.findItem(R.id.itemCancelarReq);
                item.setIcon(R.drawable.ic_cancelar_requisicao);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(MenuMainActivity.TOKEN, "");

        switch (item.getItemId()) {
            case R.id.itemCancelarReq:
                this.confirmDialog(token, requisicao.getId_requisicao());
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void confirmDialog(final String token, final int id) {
        new AlertDialog.Builder(this)
                .setTitle("Cancelar Requisição")
                .setMessage("Tem a certeza que pretende cancelar a requisição?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Singleton.getInstance(getApplicationContext()).cancelarRequisicaoAPI(getApplicationContext(), token, id);
                        finish();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

}