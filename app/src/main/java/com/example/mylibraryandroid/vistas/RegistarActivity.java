package com.example.mylibraryandroid.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.modelo.Utilizador;
import com.example.mylibraryandroid.utils.JsonParser;

public class RegistarActivity extends AppCompatActivity implements RegistarListener {
    private EditText etPrimeiroNome, etApelido, etEmail, etDataNascimento, etNif, etTelefone, etPassword, etConfPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
        setTitle("Registar");

        etPrimeiroNome = findViewById(R.id.etPrimeiroNome);
        etApelido = findViewById(R.id.etApelido);
        etEmail = findViewById(R.id.etEmail);
        etDataNascimento = findViewById(R.id.etDataNascimento);
        etNif = findViewById(R.id.etNIF);
        etTelefone = findViewById(R.id.etTelefone);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPassword);

        etPrimeiroNome.setText("Tiago");
        etApelido.setText("Lopes");
        etEmail.setText("tialop@gmail.com");
        etDataNascimento.setText("20/02/2001");
        etNif.setText("381738192");
        etTelefone.setText("910384617");
        etPassword.setText("123123123");
        etConfPassword.setText("123123123");

        Singleton.getInstance(getApplicationContext()).setRegistarListener(this);
    }

    public void onClickLogin(View view) {
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickRegistar(View view){
        if (JsonParser.isConnectionInternet(getApplicationContext())) {
            String primeiro_nome = etPrimeiroNome.getText().toString();
            String ultimo_nome = etApelido.getText().toString();
            String email = etEmail.getText().toString();
            String dta_nascimento = etDataNascimento.getText().toString();
            String nif = etNif.getText().toString();
            String num_telemovel = etTelefone.getText().toString();
            String password = etPassword.getText().toString();
            String confPassword = etConfPassword.getText().toString();

            //TODO FAZER AS FUNÇÕES PARA VERIFICAR OS DADOS

            Singleton.getInstance(getApplicationContext()).registarAPI(primeiro_nome, ultimo_nome, email, dta_nascimento, nif, num_telemovel, password, getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidateRegisto(String result) {
        if (result != null) {
            Toast.makeText(getApplicationContext(), "Registo realizado com sucesso!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Registo inválido", Toast.LENGTH_SHORT).show();
        }
    }
}
