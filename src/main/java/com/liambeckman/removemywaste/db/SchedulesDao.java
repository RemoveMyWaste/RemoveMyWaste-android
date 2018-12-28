package com.liambeckman.removemywaste.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liambeckman.removemywaste.disposal;

import java.util.List;

@Dao
public interface SchedulesDao {

    @Query("SELECT schedules.id, schedules.time_open, schedules.time_closed, schedules.cid," +
            "CASE " +
            "WHEN schedules.day_of_week = 1 THEN 'sunday' " +
            "WHEN schedules.day_of_week = 2 THEN 'monday' " +
            "WHEN schedules.day_of_week = 3 THEN 'tuesday' " +
            "WHEN schedules.day_of_week = 4 THEN 'wednesday' " +
            "WHEN schedules.day_of_week = 5 THEN 'thursday' " +
            "WHEN schedules.day_of_week = 6 THEN 'friday' " +
            "WHEN schedules.day_of_week = 7 THEN 'saturday' " +
            "ELSE 'invalid day number' " +
            "END AS day_of_week " +
            "FROM centers centers INNER JOIN schedules schedules ON centers.id = schedules.cid " +
            "WHERE (centers.name = :center); "
    )
    LiveData<List<Schedules>> searchSchedules(String center);


}
