package com.example.finalprm392.DAO;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.finalprm392.Models.Items;

import java.util.List;

@Dao
public interface ItemsDAO {

    @Insert(onConflict = REPLACE)
    void saveItem(Items items);

    @Query("SELECT * FROM items WHERE category = :category ORDER BY id ASC")
    List<Items> getAll(String category);

    @Delete
    void delete(Items items);

    @Query("UPDATE items SET checked = :checked WHERE id = :id")
    void checkUncheck(int id, boolean checked);

    @Query("SELECT COUNT(*) FROM items")
    Integer getItemsCount();

    @Query("DELETE FROM items WHERE addedby = :addedBy")
    Integer deleteAllSystemItems(String addedBy);

    @Query("DELETE FROM items WHERE category = :category")
    Integer deleteAllByCategory(String category);

    @Query("DELETE FROM items WHERE category = :category AND addedby = :addedby")
    Integer deleteAllByCategoryAndAddedBy(String category, String addedby);

    @Query("SELECT * FROM items WHERE checked = :checked ORDER BY id ASC")
    List<Items> getAllSelected(boolean checked);
    // Truy xuất toàn bộ item có trong 1 trip (dùng JOIN)
    @Query("SELECT items.* FROM items " +
            "INNER JOIN trip_items ON items.id = trip_items.itemId " +
            "WHERE trip_items.tripId = :tripId")
    List<Items> getItemsInTrip(int tripId);

    @Query("SELECT items.* FROM items " +
            "INNER JOIN trip_items ON items.id = trip_items.itemId " +
            "WHERE trip_items.tripId = :tripId AND trip_items.isPacked = 0")
    List<Items> getUnpackedItemsInTrip(int tripId);
}
