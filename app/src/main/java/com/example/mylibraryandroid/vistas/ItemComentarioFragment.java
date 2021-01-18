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
import com.example.mylibraryandroid.modelo.Comentario;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class ItemComentarioFragment extends Fragment {

    private TextView tvUtilizadorCom, tvComentario, tvDtaCom;
    private ImageView imgPerfil;

    public ItemComentarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_comentario_fragment, container, false);

        tvUtilizadorCom = view.findViewById(R.id.tvUtilizadorCom);
        tvComentario = view.findViewById(R.id.tvComentario);
        tvDtaCom = view.findViewById(R.id.tvDtaCom);
        imgPerfil = view.findViewById(R.id.imgPerfil);

        carregarComentario();

        return view;
    }

    private void carregarComentario() {
        ArrayList<Comentario> comentarios = Singleton.getInstance(getContext()).getComentariosBD();
        if(comentarios.size() > 0){
            Comentario c = comentarios.get(0);
            tvUtilizadorCom.setText(c.getId_utilizador());
            tvComentario.setText(c.getComentario());
            tvDtaCom.setText(c.getDta_comentario());

            Glide.with(getContext())
                    .load(c.getId_utilizador())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPerfil);
        }
    }
}