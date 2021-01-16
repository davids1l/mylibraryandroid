package com.example.mylibraryandroid.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.EditarPerfilListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarPerfilActivity extends AppCompatActivity implements EditarPerfilListener {

    public static final String NOME = "NOME";
    public static final String APELIDO = "APELIDO";
    public static final String NUM_TELEMOVEL = "NUM_TELEMOVEL";
    public static final String DATA_NASCIMENTO = "DATA_NASCIMENTO";
    public static final String NIF = "NIF";
    public static final String EMAIL = "EMAIL";
    private EditText etNome, etApelido, etEmail, etTelemovel, etDia, etMes, etAno, etNIF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        setTitle("Editar Dados Pessoais");

        Singleton.getInstance(this).setEditarPerfilListener(this);
        final String nome = getIntent().getStringExtra(NOME);
        final String apelido = getIntent().getStringExtra(APELIDO);
        final String numTelemovel = getIntent().getStringExtra(NUM_TELEMOVEL);
        final String dataNascimento = getIntent().getStringExtra(DATA_NASCIMENTO);
        final String nif = getIntent().getStringExtra(NIF);
        final String email = getIntent().getStringExtra(EMAIL);

        SharedPreferences sharedPreferences = this.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString(MenuMainActivity.ID,"");

        etNome = findViewById(R.id.etNome);
        etApelido = findViewById(R.id.etApelido);
        etTelemovel = findViewById(R.id.etTelemovel);
        etEmail = findViewById(R.id.etEmail);
        etDia = findViewById(R.id.etDia);
        etMes = findViewById(R.id.etMes);
        etAno = findViewById(R.id.etAno);
        etNIF = findViewById(R.id.etNIF);

        etNome.setText(nome);
        etApelido.setText(apelido);
        etTelemovel.setText(numTelemovel);
        etEmail.setText(email);
        etDia.setText(dataNascimento);
        etNIF.setText(nif);

        FloatingActionButton fab = findViewById(R.id.fabGuardarPerfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JsonParser.isConnectionInternet(getApplicationContext())) {
                        String nome = etNome.getText().toString();
                        String apelido = etApelido.getText().toString();
                        String telemovel = etTelemovel.getText().toString();
                        String dia = etDia.getText().toString();
                        String nif = etNIF.getText().toString();

                    Singleton.getInstance(getApplicationContext()).atualizarDadosLeitorAPI(getApplicationContext(), nome, apelido, telemovel, dia, nif, id);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRefreshPerfil() {
        setResult(RESULT_OK);
        finish();
    }
}