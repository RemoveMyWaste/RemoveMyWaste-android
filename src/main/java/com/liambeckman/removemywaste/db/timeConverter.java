package com.liambeckman.removemywaste.db;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class timeConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String timeToString(Date time) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String returnTime = df.format(time);
        return returnTime;
    }
}
