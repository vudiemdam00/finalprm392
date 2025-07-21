package com.example.finalprm392.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.finalprm392.Models.TripItems;

import java.util.List;
@Dao
public interface TripItemsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TripItems tripItems);

    @Query("SELECT * FROM trip_items WHERE tripId = :tripId")
    List<TripItems> getAllByTrip(int tripId);

    @Query("UPDATE trip_items SET isPacked = :packed WHERE tripId = :tripId AND itemId = :itemId")
    void setPacked(int tripId, int itemId, boolean packed);

    @Delete
    void delete(TripItems tripItems);
}
