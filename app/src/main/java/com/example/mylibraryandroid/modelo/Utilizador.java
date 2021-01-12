package com.example.mylibraryandroid.modelo;

public class Utilizador {
    private int id, bloqueado, nif, numTelemovel;
    private String primeiroNome, ultimoNome, numero, dtaBloqueado, dtaNascimento, dtaRegisto;

    public Utilizador(int id, int bloqueado, int nif, int numTelemovel, String primeiroNome, String ultimoNome, String numero, String dtaBloqueado, String dtaNascimento, String dtaRegisto) {
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

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public int getNumTelemovel() {
        return numTelemovel;
    }

    public void setNumTelemovel(int numTelemovel) {
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
