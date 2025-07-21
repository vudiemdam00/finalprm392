package com.example.finalprm392.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalprm392.Models.Trip;

import java.util.List;
@Dao
public interface TripDAO {
    @Insert
    long insertTrip(Trip trip);

    @Update
    void updateTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

    @Query("SELECT * FROM trips ORDER BY date ASC")
    List<Trip> getAllTrips();

    @Query("SELECT * FROM trips WHERE id = :tripId")
    Trip getTripById(int tripId);
}

