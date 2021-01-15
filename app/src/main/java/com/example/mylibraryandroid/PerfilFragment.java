package com.example.mylibraryandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.modelo.Utilizador;
import com.example.mylibraryandroid.vistas.MenuMainActivity;

import java.util.Objects;


public class PerfilFragment extends Fragment implements PerfilListener {


    private TextView tvNumLeitor, tvNome, tvEmail, tvNumTelemovel, tvDataNascimento, tvNIF;

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

        return view;
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

    }
}