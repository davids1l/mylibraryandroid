package com.example.mylibraryandroid.vistas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.modelo.BDHelper;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.modelo.Utilizador;
import com.example.mylibraryandroid.utils.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.FileProvider.getUriForFile;


public class PerfilFragment extends Fragment implements PerfilListener, SwipeRefreshLayout.OnRefreshListener {


    private TextView tvNumLeitor, tvNome, tvEmail, tvNumTelemovel, tvDataNascimento, tvNIF;
    private Utilizador dadosLeitor;
    private String leitorEmail;
    private ImageView imagemPerfil;
    private BDHelper bdHelper;
    static final int PERMISSAO_IMAGEM = 2;
    public static final int PICK_IMAGE = 2;
    private Bitmap bitmap;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String id;
    private String token;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(MenuMainActivity.ID,"");
        token = sharedPreferences.getString(MenuMainActivity.TOKEN,"");

        View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        bdHelper = new BDHelper(getContext());
        tvNumLeitor = view.findViewById(R.id.tvNumLeitor);
        tvNome = view.findViewById(R.id.tvNome);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvNumTelemovel = view.findViewById(R.id.tvNumTelemovel);
        tvDataNascimento = view.findViewById(R.id.tvDataNascimento);
        tvNIF = view.findViewById(R.id.tvNIF);
        imagemPerfil = view.findViewById(R.id.ivImagemPerfil);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);


        Singleton.getInstance(getContext()).setPerfilListener(this);
        Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id, token);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_perfil, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editarFoto:
                if(JsonParser.isConnectionInternet(getContext())){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_IMAGEM);
                }else {
                    Toast.makeText(getContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.editarDados:
                editarDados();
                break;

            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void logout(){
        SharedPreferences sharedPrefUser = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }


    public void editarDados(){
        if(JsonParser.isConnectionInternet(getContext())){
            Intent intent = new Intent(getContext(), EditarPerfilActivity.class);
            intent.putExtra(EditarPerfilActivity.NOME, dadosLeitor.getPrimeiroNome());
            intent.putExtra(EditarPerfilActivity.APELIDO, dadosLeitor.getUltimoNome());
            intent.putExtra(EditarPerfilActivity.NUM_TELEMOVEL, dadosLeitor.getNumTelemovel());
            intent.putExtra(EditarPerfilActivity.DATA_NASCIMENTO, dadosLeitor.getDtaNascimento());
            intent.putExtra(EditarPerfilActivity.NIF, dadosLeitor.getNif());
            startActivityForResult(intent, 1);
        }else {
            Toast.makeText(getContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(MenuMainActivity.ID,"");
        String token = sharedPreferences.getString(MenuMainActivity.TOKEN,"");*/

        switch (requestCode){
            case PICK_IMAGE:
                if(resultCode == Activity.RESULT_OK){
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String imageUrl = getStringImage(bitmap);
                    Singleton.getInstance(getContext()).perfilAlterarFoto(getContext(), imageUrl, id, token);
                    atribuirFoto(uri);
                    Toast.makeText(getContext(), "Foto alterada com sucesso!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                if(resultCode == Activity.RESULT_OK){
                    Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id, token);
                    Toast.makeText(getContext(), R.string.editarDadosSucesso, Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void atribuirFoto(Uri uri){
        Glide.with(getContext())
                .load(uri)
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagemPerfil);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onRefreshUtilizador(Utilizador utilizador) {

        tvNumLeitor.setText(utilizador.getNumero());
        String nome = utilizador.getPrimeiroNome() + " " + utilizador.getUltimoNome();
        tvNome.setText(nome);
        tvNumTelemovel.setText(utilizador.getNumTelemovel());
        String data = utilizador.getDtaNascimento();
        String dia = data.substring(8,10);
        String mes = data.substring(5,7);
        String ano = data.substring(0,4);
        tvDataNascimento.setText(dia + "/" + mes + "/" + ano);
        tvNIF.setText(utilizador.getNif());
        tvEmail.setText(utilizador.getEmail());


        Glide.with(getContext())
                .load(utilizador.getFoto_perfil())
                //.placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagemPerfil);

        dadosLeitor = utilizador;
    }

    @Override
    public void onRefreshEmailUtilizador(String email) {
        tvEmail.setText(email);
        leitorEmail = email;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSAO_IMAGEM:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    carregarFotoGaleria();
                }
                break;
        }

    }

    private void carregarFotoGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, PICK_IMAGE);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onRefresh() {
        Singleton.getInstance(getContext()).getDadosLeitorAPI(getContext(), id, token);
        swipeRefreshLayout.setRefreshing(false);
    }
}