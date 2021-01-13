package com.example.mylibraryandroid.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.FavoritoListener;
import com.example.mylibraryandroid.modelo.Livro;

import java.util.ArrayList;


public class FavoritoLivrosFragment extends Fragment implements FavoritoListener {

    private ListView lvFavoritoLivros;
    private ArrayList<Livro> FavoritoLivros;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FavoritoLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorito_livros_fragment, container, false);
    }

    @Override
    public void onRefreshFavoritoLivros(ArrayList<Livro> favoritoLivros) {

    }

    @Override
    public void onRefreshDetalhes() {

    }
}