package com.example.mylibraryandroid.modelo;

public class Utilizador {
    private int id, bloqueado, id_biblioteca;
    private String primeiroNome;
    private String ultimoNome;
    private String numero;
    private String dtaBloqueado;
    private String dtaNascimento;
    private String dtaRegisto;
    private String foto_perfil;
    private String nif;
    private String numTelemovel;
    private String email;
    private static final String IP = "http://192.168.1.100";
    private String urlImagem = IP + ":8888/frontend/web/imgs/perfil/";

    public Utilizador(int id, int bloqueado, String nif, String email, String numTelemovel, String primeiroNome, String ultimoNome, String numero, String dtaBloqueado, String dtaNascimento, String dtaRegisto, String fotoPerfil, int idBiblioteca) {
        this.id = id;
        this.bloqueado = bloqueado;
        this.nif = nif;
        this.numTelemovel = numTelemovel;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.numero = numero;
        this.dtaBloqueado = dtaBloqueado;
        this.dtaNascimento = dtaNascimento;
        this.dtaRegisto = dtaRegisto;
        this.foto_perfil = urlImagem + fotoPerfil;
        this.id_biblioteca = idBiblioteca;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_biblioteca() {
        return id_biblioteca;
    }

    public void setId_biblioteca(int id_biblioteca) {
        this.id_biblioteca = id_biblioteca;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNumTelemovel() {
        return numTelemovel;
    }

    public void setNumTelemovel(String numTelemovel) {
        this.numTelemovel = numTelemovel;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDtaBloqueado() {
        return dtaBloqueado;
    }

    public void setDtaBloqueado(String dtaBloqueado) {
        this.dtaBloqueado = dtaBloqueado;
    }

    public String getDtaNascimento() {
        return dtaNascimento;
    }

    public void setDtaNascimento(String dtaNascimento) {
        this.dtaNascimento = dtaNascimento;
    }

    public String getDtaRegisto() {
        return dtaRegisto;
    }

    public void setDtaRegisto(String dtaRegisto) {
        this.dtaRegisto = dtaRegisto;
    }
}
