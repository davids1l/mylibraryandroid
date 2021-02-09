package com.example.mylibraryandroid.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.CatalogoAdaptador;
import com.example.mylibraryandroid.adaptadores.RequisicoesAdaptador;
import com.example.mylibraryandroid.listeners.RequisicaoListener;
import com.example.mylibraryandroid.modelo.Requisicao;
import com.example.mylibraryandroid.modelo.Singleton;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class RequisicoesFragment extends Fragment implements RequisicaoListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView lvRequisicoes;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String token;
    private String id_utilizador;
    private ArrayList<Requisicao> requisicoes;

    public RequisicoesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String tokenLeitor = sharedPreferences.getString(MenuMainActivity.TOKEN, "");
        String idLeitor = sharedPreferences.getString(MenuMainActivity.ID, "");
        token = tokenLeitor;
        id_utilizador = idLeitor;

        View view = inflater.inflate(R.layout.requisicoes_livros_fragment, container, false);

        lvRequisicoes = view.findViewById(R.id.lvRequisicoes);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Singleton.getInstance(getContext()).setRequisicaoListener(this);
        Singleton.getInstance(getContext()).getRequisicoesAPI(getContext(), token, idLeitor);

        requisicoes = Singleton.getInstance(getContext()).getRequisicoesBD();

        if(requisicoes.isEmpty()){
            Toast.makeText(getContext(), "Não tem nenhuma requisição efetuada!", Toast.LENGTH_LONG).show();
        }

        lvRequisicoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesRequisicaoActivity.class);
                intent.putExtra(DetalhesRequisicaoActivity.ID_REQUISICAO, (int) id);
                Singleton.getInstance(getContext()).getRequisicaoLivrosAPI(getContext(), token, String.valueOf(id));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivityForResult(intent, 1);
            }
        });


        return view;
    }

    @Override
    public void onRefresh() {
        Singleton.getInstance(getContext()).getRequisicoesAPI(getContext(), token, id_utilizador);
        swipeRefreshLayout.setRefreshing(false);
        requisicoes = Singleton.getInstance(getContext()).getRequisicoesBD();
        if(requisicoes.isEmpty()){
            Toast.makeText(getContext(), "Não tem nenhuma requisição efetuada!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefreshRequisicao(ArrayList<Requisicao> requisicoes) {
        if (requisicoes != null){
            lvRequisicoes.setAdapter(new RequisicoesAdaptador(getContext(), requisicoes));
        }
    }
}