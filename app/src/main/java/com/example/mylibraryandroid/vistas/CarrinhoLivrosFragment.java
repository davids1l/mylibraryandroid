package com.example.mylibraryandroid.vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.CarrinhoAdaptador;
import com.example.mylibraryandroid.adaptadores.CatalogoAdaptador;
import com.example.mylibraryandroid.listeners.CarrinhoListener;
import com.example.mylibraryandroid.modelo.Biblioteca;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.BibliotecaJsonParser;
import com.example.mylibraryandroid.utils.CarrinhoJsonParser;
import com.example.mylibraryandroid.utils.LivroJsonParser;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HandshakeCompletedEvent;

public class CarrinhoLivrosFragment extends Fragment implements CarrinhoListener, SwipeRefreshLayout.OnRefreshListener {

    //private ListView lvCarrinhoLivros;
    private SwipeMenuListView lvCarrinhoLivros;
    private ArrayList<Livro> livrosCarrinho;
    private ArrayList<Biblioteca> bibliotecas;
    //private ArrayList<String> adapterList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String token;

    public CarrinhoLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String tokenLeitor = sharedPreferences.getString(MenuMainActivity.TOKEN,"");
        token = tokenLeitor;

        Singleton.getInstance(getContext()).setCarrinhoListener(this);

        final View view = inflater.inflate(R.layout.carrinho_livros_fragment, container, false);

         lvCarrinhoLivros = (SwipeMenuListView) view.findViewById(R.id.lvCarrinhoLivros);
        //lvCarrinhoLivros = view.findViewById(R.id.lvCarrinhoLivros);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(190);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_cancelar_requisicao);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        lvCarrinhoLivros.setMenuCreator(creator);

        lvCarrinhoLivros.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        if (!livrosCarrinho.isEmpty()){
                            Livro livro = livrosCarrinho.get(position);
                            Singleton.getInstance(getContext()).removerCarrinho(livro.getId_livro());
                        }

                        onRefresh();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        //Singleton.getInstance(getContext()).setCarrinhoListener(this);
        livrosCarrinho =  Singleton.getInstance(getContext()).getLivrosCarrinho();

        lvCarrinhoLivros.setAdapter(new CatalogoAdaptador(getContext(), livrosCarrinho));

        Singleton.getInstance(getContext()).getBibliotecasAPI(getContext());

        FloatingActionButton fab =  view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                final View viewDialog = getLayoutInflater().inflate(R.layout.dialog_finalizar_requisicao, null);
                final Spinner spinner = (Spinner) viewDialog.findViewById(R.id.spinnerBibliotecas);
                Button button = (Button) viewDialog.findViewById(R.id.btnFinalizar);

                bibliotecas = Singleton.getInstance(getContext()).getBibliotecas();

                //adapter para mostrar os nomes (toString do modelo Biblioteca) das bibliotecas obtidas pela API no spinner/dropdown
                final ArrayAdapter<Biblioteca> adapter = new ArrayAdapter<Biblioteca>(viewDialog.getContext(), android.R.layout.simple_spinner_dropdown_item, bibliotecas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                dialogBuilder.setView(viewDialog);
                final AlertDialog dialog = dialogBuilder.create();
                dialogBuilder.create();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(spinner.getSelectedItem() != null){
                            //obter o id_biblioteca (do objeto biblioteca) através da posição do item selecionado no spinner
                            int position = spinner.getSelectedItemPosition();
                            final int id_bib = bibliotecas.get(position).getId_biblioteca();

                            //obter id_utilizador -> shared preferences
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                            final Integer id_utilizador = Integer.valueOf(sharedPreferences.getString(MenuMainActivity.ID,""));


                            //efetuar post para a REST CUSTOM que cria a requisição
                            Singleton.getInstance(getContext()).adicionarRequisicaoAPI(getContext(), id_bib, id_utilizador);

                            dialog.dismiss();

                        } else {
                            Toast.makeText(getContext(), R.string.dialog_spinner_empty_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                /*dialogBuilder.setView(viewDialog);
                AlertDialog dialog = dialogBuilder.create();
                dialogBuilder.create();*/
                dialog.show();
            }
        });


        if (livrosCarrinho.isEmpty() || !LivroJsonParser.isConnectionInternet(getContext())){
            /*fab.setClickable(false);
            fab.setEnabled(false);*/
            fab.setVisibility(View.GONE);
            Toast.makeText(getContext(), R.string.carrinhoVazio, Toast.LENGTH_LONG).show();
        }


        lvCarrinhoLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int) id);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }


    @Override
    public void onRefresh() {
        //Singleton.getInstance(getContext()).getLivrosCarrinho();
        livrosCarrinho =  Singleton.getInstance(getContext()).getLivrosCarrinho();

        if (!livrosCarrinho.isEmpty()){
           lvCarrinhoLivros.setAdapter(new CatalogoAdaptador(getContext(), livrosCarrinho));
        } else {
            lvCarrinhoLivros.setAdapter(new CatalogoAdaptador(getContext(), livrosCarrinho));
            Toast.makeText(getContext(), R.string.carrinhoVazio, Toast.LENGTH_SHORT).show();
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshCarrinhoLivros(ArrayList<Livro> carrinho) {
        if(carrinho != null)
            lvCarrinhoLivros.setAdapter(new CatalogoAdaptador(getContext(), carrinho));
    }

    @Override
    public void onRefreshDetalhes() {

    }

}