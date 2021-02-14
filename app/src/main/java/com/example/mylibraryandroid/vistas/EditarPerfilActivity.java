package com.example.mylibraryandroid.vistas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.EditarPerfilListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.JsonParser;
import com.example.mylibraryandroid.utils.LivroJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class EditarPerfilActivity extends AppCompatActivity implements EditarPerfilListener {

    public static final String NOME = "NOME";
    public static final String APELIDO = "APELIDO";
    public static final String NUM_TELEMOVEL = "NUM_TELEMOVEL";
    public static final String DATA_NASCIMENTO = "DATA_NASCIMENTO";
    public static final String NIF = "NIF";
    private EditText etNome, etApelido, etTelemovel, etNIF, etDataNascimento;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private Button btnAlterarData;
    private String DataDia, DataMes, DataAno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        setTitle("Editar Dados Pessoais");

        //seta de go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Singleton.getInstance(this).setEditarPerfilListener(this);
        final String nome = getIntent().getStringExtra(NOME);
        final String apelido = getIntent().getStringExtra(APELIDO);
        final String numTelemovel = getIntent().getStringExtra(NUM_TELEMOVEL);
        final String dataNascimento = getIntent().getStringExtra(DATA_NASCIMENTO);
        final String nif = getIntent().getStringExtra(NIF);

        SharedPreferences sharedPreferences = this.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString(MenuMainActivity.ID, "");
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN, "");

        etNome = findViewById(R.id.etNome);
        etApelido = findViewById(R.id.etApelido);
        etTelemovel = findViewById(R.id.etTelemovel);

        etNIF = findViewById(R.id.etNIF);
        etDataNascimento = findViewById(R.id.dataEscolhida);
        etDataNascimento.setEnabled(false);
        btnAlterarData = findViewById(R.id.btnEscolherData);

        etNome.setText(nome);
        etApelido.setText(apelido);
        etTelemovel.setText(numTelemovel);
        String dia = dataNascimento.substring(8, 10);
        String mes = dataNascimento.substring(5, 7);
        String ano = dataNascimento.substring(0, 4);

        etNIF.setText(nif);
        final String data = dia + "/" + mes + "/" + ano;
        etDataNascimento.setText(data);


        btnAlterarData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditarPerfilActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                String mes = month + "";
                String dia = dayOfMonth + "";

                if(mes.length() != 2){
                    mes = "0" + mes;
                }

                if(dia.length() != 2){
                    dia = "0" + dia;
                }

                String data = dia + "/" + mes + "/" + year;
                etDataNascimento.setText(data);
            }
        };

        FloatingActionButton fab = findViewById(R.id.fabGuardarPerfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JsonParser.isConnectionInternet(getApplicationContext())) {
                    String nome = etNome.getText().toString();
                    String apelido = etApelido.getText().toString();
                    String telemovel = etTelemovel.getText().toString();

                    DataDia = etDataNascimento.getText().toString().substring(0,2);
                    DataMes = etDataNascimento.getText().toString().substring(3,5);
                    DataAno = etDataNascimento.getText().toString().substring(6,10);

                    String nif = etNIF.getText().toString();

                    if (!isNomeValid(nome)) {
                        etNome.setError(getString(R.string.campoBranco));
                        return;
                    }

                    if (!isApelidoValid(apelido)) {
                        etApelido.setError(getString(R.string.campoBranco));
                        return;
                    }

                    if (isNumTelemovelValid(telemovel) == 1) {
                        etTelemovel.setError(getString(R.string.telemovelInvalido));
                        return;
                    } else {
                        if (isNumTelemovelValid(telemovel) == 2) {
                            etTelemovel.setError(getString(R.string.telemovelErroComecar));
                            return;
                        }
                    }

                    if (!isNIFValid(nif)) {
                        etNIF.setError(getString(R.string.nifInvalido));
                        return;
                    }

                    Singleton.getInstance(getApplicationContext()).atualizarDadosLeitorAPI(getApplicationContext(), nome, apelido, telemovel, DataDia, DataMes, DataAno, nif, id, token);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRefreshPerfilEmail(String email, String token) {
        guardarInfoSharedPref(email, token);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRefreshPerfil() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        finish();
        return true;

    }

    private void guardarInfoSharedPref(String email, String token) {
        SharedPreferences sharedPrefUser = this.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();
        editor.putString(MenuMainActivity.EMAIL, email);
        editor.putString(MenuMainActivity.TOKEN, token);
        editor.apply();
    }


    //Validações para verificar os dados que vão ser atualizados
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

    private boolean isNIFValid(String nif) {
        if (nif.length() != 9) {
            return false;
        }
        return true;
    }
}