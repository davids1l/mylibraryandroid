package com.example.mylibraryandroid.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.JsonParser;

public class RegistarActivity extends AppCompatActivity implements RegistarListener {
    private EditText etPrimeiroNome, etApelido, etEmail, etDia, etMes, etAno, etNif, etTelefone, etPassword, etConfPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
        setTitle(getString(R.string.tituloRegistar));

        etPrimeiroNome = findViewById(R.id.etPrimeiroNome);
        etApelido = findViewById(R.id.etApelido);
        etEmail = findViewById(R.id.etEmail);
        etDia = findViewById(R.id.etDia);
        etMes = findViewById(R.id.etMes);
        etAno = findViewById(R.id.etAno);
        etNif = findViewById(R.id.tvNIF);
        etTelefone = findViewById(R.id.etTelefone);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPassword);

        Singleton.getInstance(getApplicationContext()).setRegistarListener(this);
    }

    public void onClickLogin(View view) {
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickRegistar(View view) {
        if (JsonParser.isConnectionInternet(getApplicationContext())) {
            String primeiro_nome = etPrimeiroNome.getText().toString();
            String ultimo_nome = etApelido.getText().toString();
            String email = etEmail.getText().toString();
            String dta_nascimento = etAno.getText().toString() + "/" + etMes.getText().toString() + "/" + etDia.getText().toString();
            String nif = etNif.getText().toString();
            String num_telemovel = etTelefone.getText().toString();
            String password = etPassword.getText().toString();
            String confPassword = etConfPassword.getText().toString();

            String ano = etAno.getText().toString();
            String mes = etMes.getText().toString();
            String dia = etDia.getText().toString();


            if (!isNomeValid(primeiro_nome)) {
                etPrimeiroNome.setError(getString(R.string.campoBranco));
                return;
            }

            if (!isApelidoValid(ultimo_nome)) {
                etApelido.setError(getString(R.string.campoBranco));
                return;
            }

            if (!isEmailValid(email)) {
                etEmail.setError(getString(R.string.etEmailInválido));
                return;
            }


            if (!isDiaBlank(dia)){
                etDia.setError(getString(R.string.campoBranco));
                return;
            }else {
                if(!isDiaValid(Integer.parseInt(dia))){
                    etDia.setError(getString(R.string.diaInvalido));
                    return;
                }
            }

            if(!isMesBlank(mes)){
                etMes.setError(getString(R.string.campoBranco));
                return;
            }else {
                if (!isMesValid(Integer.parseInt(mes))){
                    etMes.setError(getString(R.string.mesInvalido));
                    return;
                }
            }


            if (isAnoBlank(ano) == 1){
                etAno.setError(getString(R.string.campoBranco));
                return;
            }else {
                if(isAnoBlank(ano) == 2){
                    etAno.setError(getString(R.string.anoTerQuatroDigitos));
                    return;
                }
            }

            if(!isAnoValid(Integer.parseInt(ano))){
                etAno.setError(getString(R.string.anoInvalido));
                return;
            }



            if (!isNifValid(nif)) {
                etNif.setError(getString(R.string.nifInvalido));
                return;
            }

            if (isNumTelemovelValid(num_telemovel) == 1) {
                etTelefone.setError(getString(R.string.telemovelInvalido));
                return;
            } else {
                if (isNumTelemovelValid(num_telemovel) == 2) {
                    etTelefone.setError(getString(R.string.telemovelErroComecar));
                    return;
                }
            }

            if (!isPasswordValid(password)) {
                etPassword.setError(getString(R.string.passOitoCaracteres));
                return;
            }

            if (!isConfPasswordValid(confPassword, password)) {
                etConfPassword.setError(getString(R.string.confirmPassError));
                return;
            }


            Singleton.getInstance(getApplicationContext()).registarAPI(primeiro_nome, ultimo_nome, email, dta_nascimento, nif, num_telemovel, password, getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidateRegisto(String result) {
        if (result != null) {
            Toast.makeText(getApplicationContext(), R.string.registoSucesso, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), R.string.registoInvalido, Toast.LENGTH_SHORT).show();
        }
    }

    //Validações dos dados a enviar para API
    private boolean isNomeValid(String nome) {
        if (nome == null) {
            return false;
        }
        return nome.length() >= 1;
    }

    private boolean isApelidoValid(String apelido) {
        if (apelido == null) {
            return false;
        }
        return apelido.length() >= 1;
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isDtaNascimentoValid(String data) {
        if (data == null) {
            return false;
        }
        return data.length() >= 1;
    }

    private boolean isNifValid(String nif) {
        if (nif.length() != 9) {
            return false;
        }
        return true;
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

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isConfPasswordValid(String confPassword, String password) {
        if (!confPassword.equals(password)) {
            return false;
        }
        return true;
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

    private boolean isMesValid(int mes){
        if(mes < 1 || mes > 12){
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
}
