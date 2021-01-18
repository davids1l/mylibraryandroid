package com.example.mylibraryandroid.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.utils.JsonParser;

public class LoginActivity extends AppCompatActivity implements LoginListener {
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(MenuMainActivity.TOKEN,"");

        if(!token.equals("")){
            Intent intent = new Intent(this, MenuMainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);
        setTitle(getString(R.string.actLogin));

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        Singleton.getInstance(getApplicationContext()).setLoginListener(this);
    }

    public void onClickRegistar(View view) {
        Intent intent;
        intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        if (JsonParser.isConnectionInternet(getApplicationContext())) {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (!isEmailValid(email)) {
                etEmail.setError(getString(R.string.etEmailInválido));
                return;
            }

            if (!isPasswordValid(password)) {
                etPassword.setError(getString(R.string.etPasswordInválida));
                return;
            }

            Singleton.getInstance(getApplicationContext()).loginAPI(email, password, getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }

        return password.length() >= 4;
    }

    @Override
    public void onValidateLogin(String token, String id, String email, String bloqueado) {
        if (token != null) {
            if(bloqueado.equals("null")){
                guardarInfoSharedPref(token, id, email);
                Intent intent = new Intent(this, MenuMainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), R.string.contaBloqueada, Toast.LENGTH_LONG).show();
                etPassword.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.loginInvalido, Toast.LENGTH_LONG).show();
            etPassword.setText("");
        }
    }

    private void guardarInfoSharedPref(String token, String id, String email) {
        SharedPreferences sharedPrefUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();
        editor.putString(MenuMainActivity.EMAIL, email);
        editor.putString(MenuMainActivity.ID, id);
        editor.putString(MenuMainActivity.TOKEN, token);
        editor.apply();
    }
}
