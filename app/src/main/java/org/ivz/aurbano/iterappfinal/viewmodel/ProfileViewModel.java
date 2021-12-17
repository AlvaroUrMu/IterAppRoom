package org.ivz.aurbano.iterappfinal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;
import org.ivz.aurbano.iterappfinal.model.entity.ProfileCountry;
import org.ivz.aurbano.iterappfinal.model.repository.ProfileRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository repository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
    }

    public MutableLiveData<Long> getInsertResult() {
        return repository.getInsertResult();
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return repository.getInsertResults();
    }

    public void insertProfile(Profile profile, Country country) {
        repository.insertProfile(profile, country);
    }

    public void insertProfile(Profile... profiles) {
        repository.insertProfile(profiles);
    }

    public void insertCountry(Country... countries) {
        repository.insertCountry(countries);
    }

    public void updateProfile(Profile... profiles) {
        repository.updateProfile(profiles);
    }

    public void updateCountry(Country... countries) {
        repository.updateCountry(countries);
    }

    public void deleteProfiles(Profile... profiles) {
        repository.deleteProfiles(profiles);
    }

    public void deleteCountry(Country... countries) {
        repository.deleteCountry(countries);
    }

    public LiveData<List<Profile>> getProfiles() {
        return repository.getProfiles();
    }

    public LiveData<List<Country>> getCountries() {
        return repository.getCountries();
    }

    public LiveData<Profile> getProfile(long id) {
        return repository.getProfile(id);
    }

    public LiveData<Country> getCountry(long id) {
        return repository.getCountry(id);
    }

    public LiveData<List<ProfileCountry>> getAllProfile() {
        return repository.getAllProfile();
    }

    public void countriesPreload() {
        repository.countriesPreload();
    }

    public void setInit() {
        repository.setInit();
    }

    public boolean getInit() {
        return repository.getInit();
    }
}
