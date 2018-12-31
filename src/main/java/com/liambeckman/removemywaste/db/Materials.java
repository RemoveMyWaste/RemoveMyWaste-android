package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "materials")
public class Materials {
    @PrimaryKey
    @NonNull
    public int id;
    @NonNull
    public String name;
    @NonNull
    public boolean pro;
}

