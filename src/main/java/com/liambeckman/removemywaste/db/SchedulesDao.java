package com.liambeckman.removemywaste.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liambeckman.removemywaste.disposal;

import java.util.List;

@Dao
public interface SchedulesDao {

    @Query("SELECT schedules.id, schedules.time_open, schedules.time_closed, schedules.cid, schedules.day_of_week " +
            "FROM centers centers INNER JOIN schedules schedules ON centers.id = schedules.cid " +
            "WHERE (centers.id = :center); "
    )
    List<Schedules> searchSchedules(Integer center);


}
