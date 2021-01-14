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


public class ItemFavoritoFragment extends Fragment {

    private TextView tvTitulo, tvAutor, tvIdioma, tvFormato;
    private ImageView imageCapa;

    public ItemFavoritoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorito_livros_fragment, container, false);

        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvAutor = view.findViewById(R.id.tvAutor);
        tvIdioma = view.findViewById(R.id.tvIdioma);
        tvFormato = view.findViewById(R.id.tvFormato);
        imageCapa = view.findViewById(R.id.imageCapa);

        carregarFavorito();
        return view;
    }

    private void carregarFavorito() {
        ArrayList<Livro> favoritos = Singleton.getInstance(getContext()).getLivrosFavoritosBD();
        if(favoritos.size() > 0){
            Livro l = favoritos.get(0);
            tvTitulo.setText(l.getTitulo());
            tvAutor.setText(l.getId_autor()+"");
            tvIdioma.setText(l.getIdioma());
            tvFormato.setText(l.getFormato());

            Glide.with(getContext())
                    .load(l.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageCapa);
        }
    }

}