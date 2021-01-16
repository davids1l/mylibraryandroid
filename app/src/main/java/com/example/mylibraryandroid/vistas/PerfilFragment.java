package com.example.mylibraryandroid.vistas;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.modelo.Utilizador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PerfilFragment extends Fragment implements PerfilListener {


    private TextView tvNumLeitor, tvNome, tvEmail, tvNumTelemovel, tvDataNascimento, tvNIF;
    private Utilizador dadosLeitor;
    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(MenuMainActivity.ID,"");
        final String email = sharedPreferences.getString(MenuMainActivity.EMAIL,"");

        View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        tvNumLeitor = view.findViewById(R.id.tvNumLeitor);
        tvNome = view.findViewById(R.id.tvNome);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvNumTelemovel = view.findViewById(R.id.tvNumTelemovel);
        tvDataNascimento = view.findViewById(R.id.tvDataNascimento);
        tvNIF = view.findViewById(R.id.tvNIF);


        Singleton.getInstance(getContext()).setPerfilListener(this);
        Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id);

        FloatingActionButton fab = view.findViewById(R.id.fabGuardarPerfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditarPerfilActivity.class);
                intent.putExtra(EditarPerfilActivity.NOME, dadosLeitor.getPrimeiroNome());
                intent.putExtra(EditarPerfilActivity.APELIDO, dadosLeitor.getUltimoNome());
                intent.putExtra(EditarPerfilActivity.EMAIL, email);
                intent.putExtra(EditarPerfilActivity.NUM_TELEMOVEL, dadosLeitor.getNumTelemovel());
                intent.putExtra(EditarPerfilActivity.DATA_NASCIMENTO, dadosLeitor.getDtaNascimento());
                intent.putExtra(EditarPerfilActivity.NIF, dadosLeitor.getNif());
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(MenuMainActivity.ID,"");

        if(resultCode == Activity.RESULT_OK){
            Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id);
            Toast.makeText(getContext(), "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshUtilizador(Utilizador utilizador) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(MenuMainActivity.EMAIL,"");

        tvNumLeitor.setText(utilizador.getNumero());
        String nome = utilizador.getPrimeiroNome() + " " + utilizador.getUltimoNome();
        tvNome.setText(nome);
        tvEmail.setText(email);
        tvNumTelemovel.setText(utilizador.getNumTelemovel());
        tvDataNascimento.setText(utilizador.getDtaNascimento());
        tvNIF.setText(utilizador.getNif());
        dadosLeitor = utilizador;
    }
}