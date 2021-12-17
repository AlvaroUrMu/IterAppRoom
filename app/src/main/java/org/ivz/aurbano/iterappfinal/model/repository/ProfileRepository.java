package org.ivz.aurbano.iterappfinal.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;
import org.ivz.aurbano.iterappfinal.model.entity.ProfileCountry;
import org.ivz.aurbano.iterappfinal.model.room.ProfileDao;
import org.ivz.aurbano.iterappfinal.model.room.ProfileDatabase;

import java.util.List;

public class ProfileRepository {

    private static final String INIT = "init";

    private ProfileDao dao;
    private LiveData<List<ProfileCountry>> allProfile;
    private LiveData<List<Profile>> liveProfiles;
    private LiveData<List<Country>> liveCountries;
    private LiveData<Profile> liveProfile;
    private LiveData<Country> liveCountry;
    private MutableLiveData<Long> liveInsertResult;
    private MutableLiveData<List<Long>> liveInsertResults;
    private SharedPreferences preferences;

    public ProfileRepository(Context context){
        ProfileDatabase db = ProfileDatabase.getDatabase(context);
        dao = db.getDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(!getInit()){
            countriesPreload();
            setInit();
        }
    }

    public MutableLiveData<Long> getInsertResult() {
        return liveInsertResult;
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return liveInsertResults;
    }

    public void insertProfile(Profile profile, Country country) {
        Runnable r = () -> {
            profile.idcountry = insertCountryGetId(country);
            if(profile.idcountry > 0) {
                dao.insertProfile(profile);
            }
        };
        new Thread(r).start();
    }

    private long insertCountryGetId(Country country) {
        List<Long> ids = dao.insertCountry(country);
        if(ids.get(0) < 1) {
            return dao.getIdCountry(country.name);
        } else {
            return ids.get(0);
        }
    }

    public void insertProfile(Profile... profiles) {
        Runnable r = () -> {
            List<Long> resultList = dao.insertProfile(profiles);
            //liveInsertResult.postValue(resultList.get(0));
            //liveInsertResults.postValue(resultList);
            dao.insertProfile(profiles);
        };
        new Thread(r).start();
    }

    public void insertCountry(Country... countries) {
        Runnable r = () -> {
            dao.insertCountry(countries);
        };
        new Thread(r).start();
    }

    public void updateProfile(Profile... profiles) {
        Runnable r = () -> {
            dao.updateProfile(profiles);
        };
        new Thread(r).start();
    }

    public void updateCountry(Country... countries) {
        Runnable r = () -> {
            dao.updateCountry(countries);
        };
        new Thread(r).start();
    }

    public void deleteProfiles(Profile... profiles) {
        Runnable r = () -> {
            dao.deleteProfile(profiles);
        };
        new Thread(r).start();
    }

    public void deleteCountry(Country... countries) {
        Runnable r = () -> {
            dao.deleteCountry(countries);
        };
        new Thread(r).start();
    }

    public LiveData<List<Profile>> getProfiles() {
        if(liveProfiles == null) {
            liveProfiles = dao.getProfiles();
        }
        return liveProfiles;
    }

    public LiveData<List<Country>> getCountries() {
        if(liveCountries == null) {
            liveCountries = dao.getCountries();
        }
        return liveCountries;
    }

    public LiveData<Profile> getProfile(long id) {
        if(liveProfile == null) {
            liveProfile = dao.getProfile(id);
        }
        return liveProfile;
    }

    public LiveData<Country> getCountry(long id) {
        if(liveCountry == null) {
            liveCountry = dao.getCountry(id);
        }
        return liveCountry;
    }

    public LiveData<List<ProfileCountry>> getAllProfile() {
        if(allProfile == null) {
            allProfile = dao.getAllProfile();
        }
        return allProfile;
    }

    public void countriesPreload() {
        String[] countryNames = new String[] {"España", "Francia", "Italia", "Grecia", "Egipto", "Estados Unidos", "China", "India"};
        Country[] countries = new Country[countryNames.length];
        Country country;
        int cont = 0;
        for (String s: countryNames) {
            country = new Country();
            country.name = s;
            countries[cont] = country;
            cont++;
        }
        insertCountry(countries);
    }

    public void setInit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(INIT, true);
        editor.commit();
    }

    public boolean getInit() {
        return preferences.getBoolean(INIT, false);
    }
}
