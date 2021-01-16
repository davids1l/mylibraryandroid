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
    private String leitorEmail;
    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(MenuMainActivity.ID,"");

        View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        tvNumLeitor = view.findViewById(R.id.tvNumLeitor);
        tvNome = view.findViewById(R.id.tvNome);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvNumTelemovel = view.findViewById(R.id.tvNumTelemovel);
        tvDataNascimento = view.findViewById(R.id.tvDataNascimento);
        tvNIF = view.findViewById(R.id.tvNIF);


        Singleton.getInstance(getContext()).setPerfilListener(this);
        Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id);
        Singleton.getInstance(getContext()).getLeitorEmailAPI(getContext(), id);

        FloatingActionButton fab = view.findViewById(R.id.fabGuardarPerfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditarPerfilActivity.class);
                intent.putExtra(EditarPerfilActivity.NOME, dadosLeitor.getPrimeiroNome());
                intent.putExtra(EditarPerfilActivity.APELIDO, dadosLeitor.getUltimoNome());
                intent.putExtra(EditarPerfilActivity.EMAIL, leitorEmail);
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
            Singleton.getInstance(getContext()).getLeitorEmailAPI(getContext(), id);
            Toast.makeText(getContext(), "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
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
        dadosLeitor = utilizador;
    }

    @Override
    public void onRefreshEmailUtilizador(String email) {
        tvEmail.setText(email);
        leitorEmail = email;
        guardarInfoSharedPref(email);
    }

    private void guardarInfoSharedPref(String email) {
        SharedPreferences sharedPrefUser = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();
        editor.putString(MenuMainActivity.EMAIL, email);
        editor.apply();
    }
}