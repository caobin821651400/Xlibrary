package com.example.cb.test.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Address {

    @PrimaryKey(autoGenerate =true)
    private String id;

    @ColumnInfo(name = "addressName")
    private String addressName;

    public Address(String id, String addressName) {
        this.id = id;
        this.addressName = addressName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
