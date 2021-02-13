package com.example.mylibraryandroid.vistas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.FavoritoAdaptador;
import com.example.mylibraryandroid.listeners.FavoritoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.JsonParser;

import java.util.ArrayList;


public class FavoritoLivrosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FavoritoListener {

    private SwipeMenuListView lvFavoritoLivros;
    private ArrayList<Livro> favoritoLivros;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String id;
    private String token;

    public FavoritoLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(MenuMainActivity.ID, "");
        token = sharedPreferences.getString(MenuMainActivity.TOKEN,"");

        Singleton.getInstance(getContext()).setFavoritoListener(this);
        Singleton.getInstance(getContext()).getFavoritoAPI(getContext(), id, token);

        favoritoLivros = Singleton.getInstance(getContext()).getLivrosFavoritosBD();
        if(favoritoLivros.isEmpty()){
            Toast.makeText(getContext(), R.string.semFavoritos, Toast.LENGTH_SHORT).show();
        }

        View view = inflater.inflate(R.layout.favorito_livros_fragment, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        lvFavoritoLivros = (SwipeMenuListView) view.findViewById(R.id.lvFavoritoLivros);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(190);
                deleteItem.setIcon(R.drawable.ic_cancelar_requisicao);
                menu.addMenuItem(deleteItem);
            }
        };

        //definir o swipeMenu
        lvFavoritoLivros.setMenuCreator(creator);

        lvFavoritoLivros.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if(JsonParser.isConnectionInternet(getContext()) && (!favoritoLivros.isEmpty())){
                    Livro livro = favoritoLivros.get(position);
                    dialogRemover(Integer.parseInt(id), livro);
                }

                return false;
            }
        });

        lvFavoritoLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int) id);
                startActivityForResult(intent, 1);
            }
        });


        return view;
    }

    private void dialogRemover(final int id_utilizador, final Livro itemPos) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN, "");
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Remover Livro")
                .setMessage("Pretende mesmo remover o livro '"+itemPos.getTitulo()+"' dos favoritos?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Singleton.getInstance(getContext()).removerFavoritoAPI(getContext(), id_utilizador, itemPos.getId_livro(), token);
                        Toast.makeText(getContext(),"Livro removido dos favoritos!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
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
        Singleton.getInstance(getContext()).getFavoritoAPI(getContext(), id, token);
        swipeRefreshLayout.setRefreshing(false);
        if(favoritoLivros.isEmpty()){
            Toast.makeText(getContext(), R.string.semFavoritos, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshFavoritoLivros(ArrayList<Livro> favoritoLivros) {
        if(favoritoLivros != null) {
            lvFavoritoLivros.setAdapter(new FavoritoAdaptador(getContext(), favoritoLivros));
        }
    }

    @Override
    public void onRefreshDetalhes() {
        //empty
    }
}