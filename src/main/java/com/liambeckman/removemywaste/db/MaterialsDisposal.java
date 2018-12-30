package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "materials_disposal", primaryKeys = {"mid", "did"}, foreignKeys = {@ForeignKey(entity = Disposal.class,
        parentColumns = "id",
        childColumns = "did"),
        @ForeignKey(entity = Materials.class,
        parentColumns = "id",
        childColumns = "mid")})
public class MaterialsDisposal {
    @NonNull
    @ColumnInfo(name = "did")
    public int did;
    @NonNull
    @ColumnInfo(name = "mid")
    public int mid;
}

