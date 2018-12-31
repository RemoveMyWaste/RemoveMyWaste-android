package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "materials_handling", primaryKeys = {"mid", "hid"}, foreignKeys = {@ForeignKey(entity = Handling.class,
        parentColumns = "id",
        childColumns = "hid"),
        @ForeignKey(entity = Materials.class,
        parentColumns = "id",
        childColumns = "mid")})
public class MaterialsHandling {
    @NonNull
    @ColumnInfo(name = "hid")
    public int hid;
    @NonNull
    @ColumnInfo(name = "mid")
    public int mid;
}

