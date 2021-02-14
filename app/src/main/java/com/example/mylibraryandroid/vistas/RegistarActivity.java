package com.example.mylibraryandroid.vistas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.JsonParser;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RegistarActivity extends AppCompatActivity implements RegistarListener{
    private EditText etPrimeiroNome, etApelido, etEmail, etNif, etTelefone, etPassword, etConfPassword, dataEscolhida;
    private Button btnEscolherData;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private String dataSelecionada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
        setTitle(getString(R.string.tituloRegistar));

        etPrimeiroNome = findViewById(R.id.etPrimeiroNome);
        etApelido = findViewById(R.id.etApelido);
        etEmail = findViewById(R.id.etEmail);
        etNif = findViewById(R.id.tvNIF);
        etTelefone = findViewById(R.id.etTelefone);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPassword);

        dataEscolhida = findViewById(R.id.dataEscolhida);
        dataEscolhida.setEnabled(false);
        btnEscolherData = findViewById(R.id.btnEscolherData);


        btnEscolherData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegistarActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String data = dayOfMonth + "/" + month + "/" + year;
                dataEscolhida.setText(data);
                dataSelecionada = year + "/" + month + "/" + dayOfMonth;
            }
        };

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
            String dta_nascimento = dataSelecionada;
            String nif = etNif.getText().toString();
            String num_telemovel = etTelefone.getText().toString();
            String password = etPassword.getText().toString();
            String confPassword = etConfPassword.getText().toString();


            /*if (!isDataBlank(dta_nascimento)) {
                Toast.makeText(getApplicationContext(), "Data de nascimento em branco", Toast.LENGTH_SHORT).show();
                return;
            }*/


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
    private boolean isDataBlank(String data){
        if (data == null) {
            return false;
        }
        return data.length() >= 1;
    }

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
}
