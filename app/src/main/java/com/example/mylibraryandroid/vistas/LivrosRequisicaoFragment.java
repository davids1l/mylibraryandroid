package com.example.mylibraryandroid.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylibraryandroid.R;

public class LivrosRequisicaoFragment extends Fragment {


    public LivrosRequisicaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.item_requisicao_fragment, container, false)


        return inflater.inflate(R.layout.livros_requisicao_fragment, container, false);
    }
}