package org.ivz.aurbano.iterappfinal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.repository.ProfileRepository;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    private ProfileRepository repository;

    public CountryViewModel(@NonNull Application application){
        super(application);
        repository = new ProfileRepository(application);
    }

    public void insertCountry(Country... countries){
        repository.insertCountry(countries);
    }

    public void updateCountry(Country... countries){
        repository.updateCountry(countries);
    }

    public void deleteCountry(Country... countries){
        repository.deleteCountry(countries);
    }

    public LiveData<List<Country>> getCountries(){
        return repository.getCountries();
    }

    public LiveData<Country> getCountry(long id){
        return repository.getCountry(id);
    }
}
