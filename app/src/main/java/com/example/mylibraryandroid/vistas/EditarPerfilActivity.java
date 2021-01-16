package com.example.mylibraryandroid.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
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
        etDia.setText(dataNascimento.substring(8,10));
        etMes.setText(dataNascimento.substring(5,7));
        etAno.setText(dataNascimento.substring(0,4));
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
                        String mes = etMes.getText().toString();
                        String ano = etAno.getText().toString();
                        String nif = etNIF.getText().toString();
                        String email = etEmail.getText().toString();

                    if (!isNomeValid(nome)) {
                        etNome.setError("Campo em branco!");
                        return;
                    }

                    if (!isApelidoValid(apelido)) {
                        etApelido.setError("Campo em branco!");
                        return;
                    }

                    if (isNumTelemovelValid(telemovel) == 1) {
                        etTelemovel.setError("Nº de telemóvel inválido. Tem de conter 9 dígitos.");
                        return;
                    } else {
                        if (isNumTelemovelValid(telemovel) == 2) {
                            etTelemovel.setError("Nº de telemóvel tem de começar por 9");
                            return;
                        }
                    }

                    if (!isDiaBlank(dia)) {
                        etDia.setError("Campo em branco!");
                        return;
                    }else {
                        if(!isDiaValid(Integer.parseInt(dia))){
                            etDia.setError("Dia inválido. Insira um valor entre 1 e 31");
                            return;
                        }
                    }

                    if (!isMesBlank(mes)) {
                        etMes.setError("Campo em branco!");
                        return;
                    }else {
                        if (!isMesValid(Integer.parseInt(mes))){
                            etMes.setError("Mês inválido. Insira um valor entre 1 e 12.");
                            return;
                        }
                    }

                    if (isAnoBlank(ano) == 1) {
                        etAno.setError("Campo em branco!");
                        return;
                    }else {
                        if(isAnoBlank(ano) == 2){
                            etAno.setError("Ano inválido. Tem de conter 4 dígitos.");
                            return;
                        }
                    }

                    if(!isAnoValid(Integer.parseInt(ano))){
                        etAno.setError("Ano inválido.");
                        return;
                    }

                    if (!isNIFValid(nif)) {
                        etNIF.setError("NIF inválido. Tem de conter 9 dígitos.");
                        return;
                    }

                    if (!isEmailValid(email)) {
                        etEmail.setError(getString(R.string.etEmailInválido));
                        return;
                    }

                    Singleton.getInstance(getApplicationContext()).atualizarDadosLeitorAPI(getApplicationContext(), nome, apelido, telemovel, dia, mes, ano, nif, id);
                    Singleton.getInstance(getApplicationContext()).atualizarEmailLeitorAPI(getApplicationContext(), email, id);
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


    //Validações para verificar os dados que vão ser atualizados
    private boolean isNomeValid(String nome){
        if(nome == null){
            return false;
        }
        return nome.length() >= 1;
    }

    private boolean isApelidoValid(String apelido){
        if(apelido == null){
            return false;
        }
        return apelido.length() >= 1;
    }

    private int isNumTelemovelValid(String numTelemovel) {
        if (numTelemovel.length() != 9) {
            return 1;
        } else {
            if (!numTelemovel.substring(0, 1).equals("9")) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    private boolean isDiaBlank(String dia){
        if(dia == null){
            return false;
        }
        return dia.length() >= 1;
    }

    private boolean isDiaValid(int dia){
        if(dia < 1 || dia > 31){
            return false;
        }else {
            return true;
        }
    }

    private boolean isMesBlank(String mes){
        if(mes == null){
            return false;
        }
        return mes.length() >= 1;
    }

    private boolean isMesValid(int mes){
        if(mes < 1 || mes > 12){
            return false;
        }else {
            return true;
        }
    }

    private int isAnoBlank(String ano){
        if(ano == null){
            return 1;
        }else {
            if(ano.length() != 4){
                return 2;
            }
        }
        return 0;
    }

    private boolean isAnoValid(int ano){
        if(ano < 1900 || ano > 2021){
            return false;
        }else {
            return true;
        }
    }

    private boolean isNIFValid(String nif){
        if (nif.length() != 9) {
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}