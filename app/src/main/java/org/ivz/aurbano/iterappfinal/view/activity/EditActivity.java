package org.ivz.aurbano.iterappfinal.view.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.ivz.aurbano.iterappfinal.R;
import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;
import org.ivz.aurbano.iterappfinal.viewmodel.CountryViewModel;
import org.ivz.aurbano.iterappfinal.viewmodel.ProfileViewModel;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilCity, tilDate, tilImage;
    private TextInputEditText etName, etCity, etDate, etUrl;
    private Spinner spinCountry;
    private ImageView ivImage;
    private Button btEdit;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener setListener;
    private Profile profile;
    private ProfileViewModel pvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initialize();
    }

    private void initialize() {
        profile = getIntent().getExtras().getParcelable("profile");
        Log.v("hola", profile.url);

        spinCountry = findViewById(R.id.spinCountry);
        etName = findViewById(R.id.etName);
        etCity = findViewById(R.id.etCity);
        etDate = findViewById(R.id.etDate);
        etUrl = findViewById(R.id.etImage);
        ivImage = findViewById(R.id.ivProfile);
        btEdit = findViewById(R.id.btOk);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etName.setText(profile.name);
        etCity.setText(profile.city);
        etDate.setText(profile.date);
        etUrl.setText(profile.url);

        try{
            Glide.with(EditActivity.this).load(profile.url).into(ivImage);
        } catch(NullPointerException npe){
            Toast.makeText(EditActivity.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }


        ProfileViewModel pvm = new ViewModelProvider(this).get(ProfileViewModel.class);
        CountryViewModel cvm = new ViewModelProvider(this).get(CountryViewModel.class);
        cvm.getCountries().observe(this, countries -> {
            Country country = new Country();
            country.id = 0;
            country.name = getString(R.string.default_country);
            countries.add(0, country);
            ArrayAdapter<Country> adapter =
                    new ArrayAdapter<Country>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countries);
            spinCountry.setAdapter(adapter);
            spinCountry.setSelection((int) profile.idcountry + 1);
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        /*btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile p = getProfile();
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
                    Toast.makeText(EditActivity.this, "Debe seleccionar un país", Toast.LENGTH_SHORT).show();
                } else{
                    okCountry = true;
                }

                if(okName && okCity && okCountry && okDate && okImage){
                    pvm.updateProfile(p);
                    finish();
                }
            }
        });*/

        btEdit.setOnClickListener(v -> {
            Profile p = getProfile();
            if(p.isValid()){
                pvm.updateProfile(p);
                finish();
            }
        });

        Button btDelete = findViewById(R.id.btDelete);
        btDelete.setOnClickListener(v -> {
            Profile p = getProfile();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(("Eliminar postal"))
                    .setMessage("Está a punto de eliminar la postal, ¿desea continuar?")
                    .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pvm.deleteProfiles(p);
                            Toast.makeText(EditActivity.this, "La postal se ha eliminado éxitosamente", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            builder.create().show();
        });
    }

    private Profile getProfile(){
        String name = etName.getText().toString();
        String city = etCity.getText().toString();
        String date = etDate.getText().toString().trim();
        String url = etUrl.getText().toString().trim();

        Country country = (Country) spinCountry.getSelectedItem();

        Profile profile = new Profile();
        profile.name = name;
        profile.city = city;
        profile.date = date;
        profile.url = url;
        profile.idcountry = country.id;
        profile.id = this.profile.id;

        return profile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            Context context = this;

            etUrl.setText(uri.toString());

            Glide.with(EditActivity.this)
                    .load(uri)
                    .into(ivImage);
        }
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
