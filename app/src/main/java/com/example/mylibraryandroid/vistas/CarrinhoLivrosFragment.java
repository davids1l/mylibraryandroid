package com.example.mylibraryandroid.vistas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CarrinhoLivrosFragment extends Fragment implements CarrinhoListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView lvCarrinhoLivros;
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

        final View view = inflater.inflate(R.layout.carrinho_livros_fragment, container, false);
        lvCarrinhoLivros = view.findViewById(R.id.lvCarrinhoLivros);

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
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                final View viewDialog = getLayoutInflater().inflate(R.layout.dialog_finalizar_requisicao, null);
                final Spinner spinner = (Spinner) viewDialog.findViewById(R.id.spinnerBibliotecas);
                Button button = (Button) viewDialog.findViewById(R.id.btnFinalizar);

                bibliotecas = Singleton.getInstance(getContext()).getBibliotecas();

                //adapter para mostrar os nomes (toString do modelo Biblioteca) das bibliotecas obtidas pela API no spinner/dropdown
                final ArrayAdapter<Biblioteca> adapter = new ArrayAdapter<Biblioteca>(viewDialog.getContext(), android.R.layout.simple_spinner_dropdown_item, bibliotecas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(spinner.getSelectedItem() != null){
                            //obter o id_biblioteca (do objeto biblioteca) através da posição do item selecionado no spinner
                            int position = spinner.getSelectedItemPosition();
                            int id_bib = bibliotecas.get(position).getId_biblioteca();

                            //obter id_utilizador -> shared preferences
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                            String id_utilizador = sharedPreferences.getString(MenuMainActivity.ID,"");

                            //efetuar post para a REST CUSTOM que cria a requisição
                            Singleton.getInstance(getContext()).adicionarRequisicaoAPI(getContext(), id_bib, Integer.parseInt(id_utilizador));

                        } else {
                            Toast.makeText(getContext(), R.string.dialog_spinner_empty_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dialogBuilder.setView(viewDialog);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });

        if (livrosCarrinho.isEmpty() || !LivroJsonParser.isConnectionInternet(getContext())){
            /*fab.setClickable(false);
            fab.setEnabled(false);*/
            fab.setVisibility(View.GONE);
            Toast.makeText(getContext(), R.string.carrinhoVazio, Toast.LENGTH_LONG).show();
        }



        //TODO: Fazer o listner onItemClick para mostrar os detalhes do livro



        return view;
    }

    @Override
    public void onRefresh() {
        Singleton.getInstance(getContext()).getLivrosCarrinho();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshCarrinhoLivros(ArrayList<Livro> carrinho) {
        if(carrinho != null)
            lvCarrinhoLivros.setAdapter(new CarrinhoAdaptador(getContext(), carrinho));
    }
}