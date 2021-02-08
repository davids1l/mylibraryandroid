package com.example.mylibraryandroid.vistas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.modelo.BDHelper;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.modelo.Utilizador;
import com.example.mylibraryandroid.utils.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PerfilFragment extends Fragment implements PerfilListener {


    private TextView tvNumLeitor, tvNome, tvEmail, tvNumTelemovel, tvDataNascimento, tvNIF;
    private Utilizador dadosLeitor;
    private String leitorEmail;
    private ImageView imagemPerfil;
    private BDHelper bdHelper;
    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(MenuMainActivity.ID,"");
        String token = sharedPreferences.getString(MenuMainActivity.TOKEN,"");

        View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        bdHelper = new BDHelper(getContext());
        tvNumLeitor = view.findViewById(R.id.tvNumLeitor);
        tvNome = view.findViewById(R.id.tvNome);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvNumTelemovel = view.findViewById(R.id.tvNumTelemovel);
        tvDataNascimento = view.findViewById(R.id.tvDataNascimento);
        tvNIF = view.findViewById(R.id.tvNIF);
        imagemPerfil = view.findViewById(R.id.ivImagemPerfil);


        Singleton.getInstance(getContext()).setPerfilListener(this);
        Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id, token);
        //Singleton.getInstance(getContext()).getLeitorEmailAPI(getContext(), id, token);

        FloatingActionButton fab = view.findViewById(R.id.fabGuardarPerfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(JsonParser.isConnectionInternet(getContext())){
                    Intent intent = new Intent(getContext(), EditarPerfilActivity.class);
                    intent.putExtra(EditarPerfilActivity.NOME, dadosLeitor.getPrimeiroNome());
                    intent.putExtra(EditarPerfilActivity.APELIDO, dadosLeitor.getUltimoNome());
                    intent.putExtra(EditarPerfilActivity.NUM_TELEMOVEL, dadosLeitor.getNumTelemovel());
                    intent.putExtra(EditarPerfilActivity.DATA_NASCIMENTO, dadosLeitor.getDtaNascimento());
                    intent.putExtra(EditarPerfilActivity.NIF, dadosLeitor.getNif());
                    startActivityForResult(intent, 1);
                }else {
                    Toast.makeText(getContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(MenuMainActivity.ID,"");
        String token = sharedPreferences.getString(MenuMainActivity.TOKEN,"");

        if(resultCode == Activity.RESULT_OK){
            Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id, token);
            //Singleton.getInstance(getContext()).getLeitorEmailAPI(getContext(), id, token);
            Toast.makeText(getContext(), R.string.editarDadosSucesso, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRefreshUtilizador(Utilizador utilizador) {

        tvNumLeitor.setText(utilizador.getNumero());
        String nome = utilizador.getPrimeiroNome() + " " + utilizador.getUltimoNome();
        tvNome.setText(nome);
        tvNumTelemovel.setText(utilizador.getNumTelemovel());
        String data = utilizador.getDtaNascimento();
        String dia = data.substring(8,10);
        String mes = data.substring(5,7);
        String ano = data.substring(0,4);
        tvDataNascimento.setText(dia + "/" + mes + "/" + ano);
        tvNIF.setText(utilizador.getNif());
        tvEmail.setText(utilizador.getEmail());
        Glide.with(getContext())
                .load(utilizador.getFoto_perfil())
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagemPerfil);
        dadosLeitor = utilizador;
        //Singleton.getInstance(getContext()).atualizarBDEditarPerfil(getContext(), dadosLeitor, leitorEmail);
    }

    @Override
    public void onRefreshEmailUtilizador(String email) {
        tvEmail.setText(email);
        leitorEmail = email;

        //Singleton.getInstance(getContext()).adicionarDadosLeitorBD(getContext(), dadosLeitor, email);
    }

}