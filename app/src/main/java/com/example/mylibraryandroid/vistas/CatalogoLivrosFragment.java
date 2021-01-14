package com.example.mylibraryandroid.vistas;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.CatalogoAdaptador;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class CatalogoLivrosFragment extends Fragment implements CatalogoListener {

    //private CatalogoLivrosViewModel mViewModel;
    private ListView lvCatalogoLivros;
    private ArrayList<Livro> catalogoLivros;

    //TODO: searchView e swipeRefresh


    public CatalogoLivrosFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.catalogo_livros_fragment, container, false);
        lvCatalogoLivros = view.findViewById(R.id.lvCatalogoLivros);

        //TODO: implementar o swipeRefresh

        Singleton.getInstance(getContext()).setCatalogoListener(this);
        Singleton.getInstance(getContext()).getCatalogoAPI(getContext());

        return view;
    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CatalogoLivrosViewModel.class);
        // TODO: Use the ViewModel
    }*/


    @Override
    public void onRefreshCatalogoLivros(ArrayList<Livro> catalogoLivros) {
        if(catalogoLivros != null)
            lvCatalogoLivros.setAdapter(new CatalogoAdaptador(getContext(), catalogoLivros));
    }

    @Override
    public void onRefreshDetalhes() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       /* if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case
            }
        }*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        /*inflater.inflate(R.menu.menu_pesquisa, menu); //Injetar layout do menu nesta activity

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Livro> tempLivros = new ArrayList<>();

                for (Livro l : Singleton.getInstance(getContext()).getCatalogoBD())
                    if (l.getTitulo().toLowerCase().contains(newText.toLowerCase())){
                        tempLivros.add(l);
                    }

                lvCatalogoLivros.setAdapter(new CatalogoAdaptador(getContext(), tempLivros));

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);*/
    }


}