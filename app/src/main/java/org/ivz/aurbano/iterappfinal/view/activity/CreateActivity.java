package org.ivz.aurbano.iterappfinal.view.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.ivz.aurbano.iterappfinal.R;
import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;
import org.ivz.aurbano.iterappfinal.viewmodel.CountryViewModel;
import org.ivz.aurbano.iterappfinal.viewmodel.ProfileViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CreateActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilCity, tilDate, tilImage;
    private TextInputEditText etName, etCity, etDate, etUrl;
    private Spinner spinCountry;
    private ImageView ivImage;
    private Button btAdd;
    private Profile profile;
    private ProfileViewModel pvm;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            Context context = this;

            etUrl.setText(uri.toString());

            Glide.with(CreateActivity.this)
                    .load(uri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivImage);
        }
    }

    private void initialize() {
        tilName = findViewById(R.id.tilName);
        tilCity = findViewById(R.id.tilCity);
        tilDate = findViewById(R.id.tilDate);
        tilImage = findViewById(R.id.tilImage);
        spinCountry = findViewById(R.id.spinCountry);
        etName = findViewById(R.id.etName);
        etCity = findViewById(R.id.etCity);
        etDate = findViewById(R.id.etDate);
        etUrl = findViewById(R.id.etImage);
        ivImage = findViewById(R.id.imageView);
        btAdd = findViewById(R.id.btOk);
        Button btDelete = findViewById(R.id.btDelete);
        btDelete.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*etUrl.setOnFocusChangeListener((v, hasFocus) -> {
            //String url;
            if(!hasFocus){
                if(!etUrl.getText().toString().isEmpty()){
                    //url = pvm.getUrl(etName.getText().toString());
                    Glide.with(this).load(etUrl).into(ivImage);
                    //etUrl.setText(url);
                }
            }
        });*/

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + " de " + getMonthFormat(month) + " de " + year;
                        etDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        etUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        getViewModel();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = getProfile();
                boolean okName = false, okCity = false, okCountry = false, okDate = false, okImage = false;

                if(isEmpty(etName.getText().toString())){
                    okName = false;
                    tilName.setError("Debe introducir un nombre");
                } else{
                    okName = true;
                    tilName.setErrorEnabled(false);
                }

                if(isEmpty(etCity.getText().toString())){
                    okCity = false;
                    tilCity.setError("Debe introducir una ciudad");
                } else{
                    okCity = true;
                    tilCity.setErrorEnabled(false);
                }

                if(isEmpty(etDate.getText().toString())){
                    okDate = false;
                    tilDate.setError("Debe introducir una fecha");
                } else{
                    okDate = true;
                    tilDate.setErrorEnabled(false);
                }

                if(isEmpty(etUrl.getText().toString())){
                    okImage = false;
                    tilImage.setError("Debe introducir una imagen");
                } else{
                    okImage = true;
                    tilImage.setErrorEnabled(false);
                }

                if(noSelection(spinCountry.getSelectedItemId())){
                    okCountry = false;
                    Toast.makeText(CreateActivity.this, "Debe seleccionar un país", Toast.LENGTH_SHORT).show();
                } else{
                    okCountry = true;
                }

                if(okName && okCity && okCountry && okDate && okImage){
                    addProfile(profile);
                    finish();
                }
            }
        });
        //defineAddListener();
    }

    /*private void defineAddListener(){

        btAdd.setOnClickListener(v -> {
            Profile profile = getProfile();
            if(profile.isValid()){
                addProfile(profile);
                finish();
            } else{
                Toast.makeText(this, "No es válido", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private Profile getProfile(){
        String name = etName.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String url = etUrl.getText().toString().trim();

        Country country = (Country) spinCountry.getSelectedItem();

        Profile profile = new Profile();
        profile.name = name;
        profile.city = city;
        profile.date = date;
        profile.url = url;
        profile.idcountry = country.id;

        return profile;
    }

    private void addProfile(Profile profile){
        pvm.insertProfile(profile);
    }

    private void getViewModel(){
        pvm = new ViewModelProvider(this).get(ProfileViewModel.class);

        /*pvm.getInsertResults().observe(this, list -> {
            if(list.get(0) > 0){
                if(firstTime){
                    firstTime = false;
                    alert();
                } else{
                    cleanFields();
                }
            } else{
                Toast.makeText(this, "No insertado", Toast.LENGTH_LONG).show();
            }
        });*/

        CountryViewModel cvm = new ViewModelProvider(this).get(CountryViewModel.class);
        cvm.getCountries().observe(this, countries -> {
            Country country = new Country();
            country.id = 0;
            country.name = getString(R.string.default_country);
            countries.add(0, country);
            ArrayAdapter<Country> adapter =
                    new ArrayAdapter<Country>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countries);
            spinCountry.setAdapter(adapter);
        });


    }

    /*private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(("Insertar"))
                .setMessage("La postal se ha insertado correctamente, ¿desea seguir agregando postales?")
                .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanFields();
                    }
                });
        builder.create().show();
    }*/

    private void cleanFields(){
        etName.setText("");
        etCity.setText("");
        etDate.setText("");
        etUrl.setText("");
        spinCountry.setSelection(0);
        //Toast.makeText(this, "8 inserted", Toast.LENGTH_LONG).show();
    }

    private String getMonthFormat(int month){
        if(month == 1)
            return "enero";
        if(month == 2)
            return "febrero";
        if(month == 3)
            return "marzo";
        if(month == 4)
            return "abril";
        if(month == 5)
            return "mayo";
        if(month == 6)
            return "junio";
        if(month == 7)
            return "julio";
        if(month == 8)
            return "agosto";
        if(month == 9)
            return "septiembre";
        if(month == 10)
            return "octubre";
        if(month == 11)
            return "noviembre";
        if(month == 12)
            return "diciembre";

        return "enero";
    }

    private boolean isEmpty(String texto){
        if(texto.isEmpty()){
            return true;
        } else{
            return false;
        }
    }

    private boolean noSelection(long valor){
        if(valor == 0){
            return true;
        } else{
            return false;
        }
    }
}