package com.example.finalprm392.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.finalprm392.DAO.ItemsDAO;
import com.example.finalprm392.DAO.TripDAO;
import com.example.finalprm392.DAO.TripItemsDAO;
import com.example.finalprm392.Models.Converters;
import com.example.finalprm392.Models.Items;
import com.example.finalprm392.Models.Trip;
import com.example.finalprm392.Models.TripItems;

@Database(
        entities = {Items.class, Trip.class, TripItems.class},
        version = 3,
        exportSchema = false
)
@TypeConverters(Converters.class)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static final String DATABASE_NAME = "MyDb";

    public synchronized static RoomDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract ItemsDAO mainDAO();
    public abstract TripDAO tripDAO();
    public abstract TripItemsDAO tripItemsDAO();
}
