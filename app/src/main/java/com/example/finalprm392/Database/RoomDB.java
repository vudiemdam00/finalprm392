package com.example.finalprm392.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finalprm392.DAO.ItemsDAO;
import com.example.finalprm392.Models.Items;

@Database(entities = {Items.class}, version = 2, exportSchema = false)
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

    // ✅ Tên method có thể tùy chọn, miễn sao bạn gọi đúng tên ở nơi sử dụng
    public abstract ItemsDAO mainDAO();
}
