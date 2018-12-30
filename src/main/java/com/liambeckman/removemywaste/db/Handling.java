package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "handlingInstructions")
public class Handling {
    @PrimaryKey
    @NonNull
    public int id;
    public String instructions;
}

