package org.ivz.aurbano.iterappfinal.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;
import org.ivz.aurbano.iterappfinal.model.entity.ProfileCountry;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertProfile(Profile... profiles);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Profile profile);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertCountry(Country... countries);

    @Update
    int updateProfile(Profile... profiles);

    @Update
    int updateCountry(Country... countries);

    @Delete
    int deleteProfile(Profile... profiles);

    @Delete
    int deleteCountry(Country... countries);

    @Query("delete from profile")
    int deleteAllProfiles();

    @Query("delete from country")
    int deleteAllCountries();

    @Query("select * from profile order by name asc")
    LiveData<List<Profile>> getProfiles();

    @Query("select * from country order by name asc")
    LiveData<List<Country>> getCountries();

    @Query("select p.*, c.id country_id, c.name country_name from profile p join country c on p.idcountry = c.id order by name asc")
    LiveData<List<ProfileCountry>> getAllProfile();

    @Query("select * from profile where id = :id")
    LiveData<Profile> getProfile(long id);

    @Query("select * from country where id = :id")
    LiveData<Country> getCountry(long id);

    @Query("select id from country where name = :name")
    long getIdCountry(String name);

    @Query("select * from country where name = :name")
    Country getCountry(String name);
}
