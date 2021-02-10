package com.example.mylibraryandroid.vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.LivrosDetalhesReqAdaptador;
import com.example.mylibraryandroid.adaptadores.RequisicoesAdaptador;
import com.example.mylibraryandroid.listeners.LivrosDetalhesReqListener;
import com.example.mylibraryandroid.listeners.RequisicaoLivroListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.RequisicaoLivro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class LivrosRequisicaoFragment extends Fragment implements LivrosDetalhesReqListener {

    private ListView lvLivrosRequisicao;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Livro> livrosDetalhesRequisicao;

    private String token;

    public LivrosRequisicaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.livros_requisicao_fragment, container, false);
        lvLivrosRequisicao = view.findViewById(R.id.lvLivrosRequisicao);

        livrosDetalhesRequisicao = Singleton.getInstance(getContext()).getLivrosDetalhesReq();

        lvLivrosRequisicao.setAdapter(new LivrosDetalhesReqAdaptador(getContext(), livrosDetalhesRequisicao));


        lvLivrosRequisicao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int) id);
                startActivityForResult(intent, 1);
            }
        });


        return view;
    }

    @Override
    public void onRefreshDetalhesReq(ArrayList<Livro> livrosDetalhesRequisicao) {
        if(livrosDetalhesRequisicao != null){
            lvLivrosRequisicao.setAdapter(new LivrosDetalhesReqAdaptador(getContext(), livrosDetalhesRequisicao));
        }
    }
}

