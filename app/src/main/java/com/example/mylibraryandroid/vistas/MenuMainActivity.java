package com.example.mylibraryandroid.vistas;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.mylibraryandroid.PerfilFragment;
import com.example.mylibraryandroid.R;
import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EMAIL = "EMAIL";
    public static final String TOKEN = "TOKEN";
    public static final String ID = "ID";
    public static final String PREF_INFO_USER = "PREF_INFO_USER";

    private FragmentManager fragmentManager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String email = ""; //TODO: substituir por Nome - utilizador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();



        carregarCabecalho();
        carregarFragmentoInicial();

    }

    private void carregarCabecalho() {
        SharedPreferences sharedPrefInfoUser = getSharedPreferences(PREF_INFO_USER, Context.MODE_PRIVATE);

        email = sharedPrefInfoUser.getString(EMAIL, "Sem Email");

        View hView = navigationView.getHeaderView(0);
        TextView tvEmail = hView.findViewById(R.id.tvEmail);
        tvEmail.setText(email);
    }

    private void carregarFragmentoInicial() {
        //navigationView.setCheckedItem(R.id.nav_catalogo);
        Fragment fragment = new CatalogoLivrosFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        setTitle(R.string.menu_catalogo);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item .getItemId()) {
            case R.id.nav_catalogo:
                fragment = new CatalogoLivrosFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_favoritos:
                fragment = new FavoritoLivrosFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_perfil:
                fragment = new PerfilFragment();
                setTitle(item.getTitle());
                break;
        }

        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
