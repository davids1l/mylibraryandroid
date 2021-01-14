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


public class ItemCatalogoFragment extends Fragment {

    private TextView tvTitulo, tvAutor, tvIdioma, tvFormato;
    private ImageView imgCapa;


    public ItemCatalogoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_catalogo_fragment, container, false);

        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvAutor = view.findViewById(R.id.tvAutor);
        tvIdioma = view.findViewById(R.id.tvIdioma);
        tvFormato = view.findViewById(R.id.tvFormato);
        imgCapa = view.findViewById(R.id.imageCapa);
        carregarLivro();

        return view;
    }

    private void carregarLivro() {
        ArrayList<Livro> catalogo = Singleton.getInstance(getContext()).getCatalogoBD();
        if (catalogo.size() > 0){
            Livro l = catalogo.get(0);
            tvTitulo.setText(l.getTitulo());
            tvAutor.setText(l.getId_autor());
            tvIdioma.setText(l.getIdioma());
            tvFormato.setText(l.getFormato());

            Glide.with(this)
                    .load(l.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }
    }
}