package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "centers")
public class Centers {
    @PrimaryKey
    @NonNull
    public int id;
    @NonNull
    public String name;

    public Integer street_number;
    public String street_direction;
    public String street_name;
    public String street_type;
    public String city;
    public String state;
    public String zip;
}

