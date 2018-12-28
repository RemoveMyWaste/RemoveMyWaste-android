package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.huma.room_for_asset.RoomAsset;

@Database(entities = {Materials.class, Centers.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract MaterialsDao materialsModel();

    public abstract CentersDao centersModel();

    //public abstract HandlingDao handlingInstructionsModel();

    //public abstract DisposalDao disposalInstructionsModel();

    //public abstract SchedulesDao schedulesModel();

    public static AppDatabase buildDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    RoomAsset.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
        }
        Log.d("mydatabase", INSTANCE.toString());
        //Log.d("MyApp", INSTANCE.MaterialsDao());
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
