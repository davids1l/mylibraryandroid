package com.example.mylibraryandroid.vistas;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.CatalogoAdaptador;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.listeners.EditoraListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class CatalogoLivrosFragment extends Fragment implements CatalogoListener, SwipeRefreshLayout.OnRefreshListener {

    //private CatalogoLivrosViewModel mViewModel;
    private ListView lvCatalogoLivros;
    private ArrayList<Livro> catalogoLivros;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;

    public CatalogoLivrosFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.catalogo_livros_fragment, container, false);
        lvCatalogoLivros = view.findViewById(R.id.lvCatalogoLivros);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String idUtilizador = sharedPreferences.getString(MenuMainActivity.ID, "");

        Singleton.getInstance(getContext()).getFavoritoAPI(getContext(), idUtilizador);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Singleton.getInstance(getContext()).setCatalogoListener(this);
        Singleton.getInstance(getContext()).getCatalogoAPI(getContext());

        //método GET - API para obter os autores
        Singleton.getInstance(getContext()).getAutoresAPI(getContext());

        //método GET - API para obter as bibliotecas
        Singleton.getInstance(getContext()).getBibliotecasAPI(getContext());

        //método GET - API para obter as editoras
        Singleton.getInstance(getContext()).getEditorasAPI(getContext());

        lvCatalogoLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int) id);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CatalogoLivrosViewModel.class);
        // TODO: Use the ViewModel
    }*/

    @Override
    public void onResume() {
        super.onResume();
        Singleton.getInstance(getContext()).setCatalogoListener(this);
    }

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

        inflater.inflate(R.menu.menu_pesquisa, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return true; }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Livro> tempLivros = new ArrayList<>();

                for (Livro l : Singleton.getInstance(getContext()).getCatalogoBD())
                    if (l.getTitulo().toLowerCase().contains(newText.toLowerCase())){
                        tempLivros.add(l);
                    }

                lvCatalogoLivros.setAdapter(new CatalogoAdaptador(getContext(), tempLivros));

                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onRefresh() {
        Singleton.getInstance(getContext()).getCatalogoAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }
}