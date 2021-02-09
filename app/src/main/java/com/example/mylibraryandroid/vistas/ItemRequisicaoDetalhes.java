package com.example.mylibraryandroid.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;


public class ItemRequisicaoDetalhes extends Fragment {

    private TextView tvTitulo, tvAutor;
    private ImageView imgCapaDetalhesReq;

    public ItemRequisicaoDetalhes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.item_requisicao_detalhes_fragment, container, false);

        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvAutor = view.findViewById(R.id.tvAutor);
        imgCapaDetalhesReq = view.findViewById(R.id.imgCapaDetalhesReq);
        //carregarLivroReq();

        return view;
    }

    private void carregarLivroReq() {
        ArrayList<Livro> livrosReq = Singleton.getInstance(getContext()).obterLivrosRequisicao();

        if (livrosReq.size() > 0){

            Livro l = livrosReq.get(0);
            String nomeAutor = Singleton.getInstance(getContext()).getNomeAutor(l.getId_autor());

            tvTitulo.setText(l.getTitulo());
            tvAutor.setText(nomeAutor);

            Glide.with(this)
                    .load(l.getCapa())
                    .placeholder(R.drawable.ic_loading_capa)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapaDetalhesReq);
        }
    }

}