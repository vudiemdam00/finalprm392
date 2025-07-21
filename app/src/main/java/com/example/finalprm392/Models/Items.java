package com.example.finalprm392.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "items")  // ✅ sửa lại chính tả
public class Items implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @ColumnInfo(name = "itemname")  // ✅ sửa từ itaemname
    private String itemname;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "addedby")
    private String addedby;

    @ColumnInfo(name = "checked")
    private Boolean checked = false;
    @ColumnInfo(name = "trip_id")
    public Integer tripId;
    public Items() {
    }

    public Items(String itemname, String category, String addedby, Boolean checked) {
        this.itemname = itemname;
        this.category = category;
        this.addedby = addedby;
        this.checked = checked;
    }

    public Items(String itemname, String category, Boolean checked) {
        this.addedby = "system";
        this.itemname = itemname;
        this.category = category;
        this.checked = checked;
    }

    public Items(String addedby, Integer tripId, String itemname, int id, Boolean checked, String category) {
        this.addedby = addedby;
        this.tripId = tripId;
        this.itemname = itemname;
        this.id = id;
        this.checked = checked;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
}
