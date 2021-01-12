package com.example.mylibraryandroid.vistas;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class CatalogoLivrosFragment extends Fragment {

    //private CatalogoLivrosViewModel mViewModel;
    private ListView lvCatalogoLivros;
    private ArrayList<Livro> catalogoLivros;
    //TODO: searchView e swipeRefresh


    /*public static CatalogoLivrosFragment newInstance() {
        return new CatalogoLivrosFragment();
    }*/

    public CatalogoLivrosFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.catalogo_livros_fragment, container, false);

        View view = inflater.inflate(R.layout.catalogo_livros_fragment, container, false);
        lvCatalogoLivros = view.findViewById(R.id.lvCatalogoLivros);

        //TODO: implementar o swipeRefresh

        Singleton.getInstance(getContext()).setCatalogoListener(this);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CatalogoLivrosViewModel.class);
        // TODO: Use the ViewModel
    }



}