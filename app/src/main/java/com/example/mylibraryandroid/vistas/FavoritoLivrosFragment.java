package com.example.mylibraryandroid.vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.FavoritoAdaptador;
import com.example.mylibraryandroid.listeners.FavoritoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;


public class FavoritoLivrosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FavoritoListener {

    private ListView lvFavoritoLivros;
    private ArrayList<Livro> favoritoLivros;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FavoritoLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.favorito_livros_fragment, container, false);
        lvFavoritoLivros = view.findViewById(R.id.lvFavoritoLivros);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Singleton.getInstance(getContext()).setFavoritoListener(this);
        Singleton.getInstance(getContext()).getFavoritoAPI(getContext());

        // TODO Implementar clique nos items para mostrar os detalhes dos favoritos referentes ao livro clicado

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Livro> tempLivros = new ArrayList<>();
                for(Livro l : Singleton.getInstance(getContext()).getLivrosFavoritosBD()){
                    if(l.getTitulo().toLowerCase().contains(newText.toLowerCase())){
                        tempLivros.add(l);
                    }
                    lvFavoritoLivros.setAdapter(new FavoritoAdaptador(getContext(), tempLivros));
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Fechar a searchView
        if(searchView != null){
            searchView.onActionViewCollapsed();
        }
        Singleton.getInstance(getContext()).setFavoritoListener(this);
    }

    @Override
    public void onRefresh() {
        Singleton.getInstance(getContext()).getFavoritoAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshFavoritoLivros(ArrayList<Livro> favoritoLivros) {
        if(favoritoLivros != null)
            lvFavoritoLivros.setAdapter(new FavoritoAdaptador(getContext(), favoritoLivros));
    }

    @Override
    public void onRefreshDetalhes() {
        //empty
    }
}