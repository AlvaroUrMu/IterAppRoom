package org.ivz.aurbano.iterappfinal.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.ivz.aurbano.iterappfinal.R;
import org.ivz.aurbano.iterappfinal.model.entity.ProfileCountry;
import org.ivz.aurbano.iterappfinal.view.adapter.ProfileAdapter;
import org.ivz.aurbano.iterappfinal.viewmodel.ProfileViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvPerfil = findViewById(R.id.rvPerfil);
        rvPerfil.setLayoutManager(new LinearLayoutManager(this));

        ProfileViewModel pvm = new ViewModelProvider(this).get(ProfileViewModel.class);
        ProfileAdapter profileAdapter = new ProfileAdapter(this);

        rvPerfil.setAdapter(profileAdapter);

        LiveData<List<ProfileCountry>> listaProfileCountry = pvm.getAllProfile();
        listaProfileCountry.observe(this, profileCountries -> {
            profileAdapter.setProfileList(profileCountries);
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int mode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES?AppCompatDelegate.MODE_NIGHT_NO:AppCompatDelegate.MODE_NIGHT_YES;

        switch(item.getItemId()){
            case  R.id.app_bar_switch:
                if(mode == AppCompatDelegate.MODE_NIGHT_NO){
                    AppCompatDelegate.setDefaultNightMode(mode);
                } else if(mode == AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(mode);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}