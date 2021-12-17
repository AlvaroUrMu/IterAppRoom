package org.ivz.aurbano.iterappfinal.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;

@Database(entities = {Profile.class, Country.class}, version = 1, exportSchema = false)
public abstract class ProfileDatabase extends RoomDatabase{

    public abstract ProfileDao getDao();

    private static volatile ProfileDatabase INSTANCE;

    public static ProfileDatabase getDatabase(Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProfileDatabase.class, "profile").build();
        }
        return INSTANCE;
    }
}
