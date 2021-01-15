package com.example.mylibraryandroid.listeners;

public interface LoginListener {
    void onValidateLogin(String dados, String token, String email, String bloqueado);
}
