package com.example.finalprm392.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "trip_items",
        primaryKeys = {"tripId", "itemId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Trip.class,
                        parentColumns = "id",
                        childColumns = "tripId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Items.class,
                        parentColumns = "id",
                        childColumns = "itemId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class TripItems {

    @NonNull
    @ColumnInfo(name = "tripId")
    public int tripId;

    @NonNull
    @ColumnInfo(name = "itemId")
    public int itemId;

    @ColumnInfo(name = "isPacked")
    public boolean isPacked;

    public TripItems(boolean isPacked, int itemId, int tripId) {
        this.isPacked = isPacked;
        this.itemId = itemId;
        this.tripId = tripId;
    }

    public TripItems() {
    }

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}

