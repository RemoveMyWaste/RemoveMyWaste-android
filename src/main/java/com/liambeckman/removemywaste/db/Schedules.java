package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.time.LocalTime;
import java.util.Date;

@Entity(tableName = "schedules", foreignKeys = @ForeignKey(entity = Centers.class,
        parentColumns = "id",
        childColumns = "cid"))
@TypeConverters({Converters.class})
public class Schedules {
    @PrimaryKey
    @NonNull
    public int id;

    public Integer day_of_week;

    public String time_open;
    public String time_closed;

    @ColumnInfo(name = "cid")
    @NonNull
    public int cid;
}

