package com.example.cb.test.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Address {

    @PrimaryKey(autoGenerate =true)
    private int id;

    @ColumnInfo(name = "addressName")
    private String addressName;

    public Address(int id, String addressName) {
        this.id = id;
        this.addressName = addressName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
