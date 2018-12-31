package com.liambeckman.removemywaste.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liambeckman.removemywaste.centers;
import com.liambeckman.removemywaste.disposal;

import java.util.List;

@Dao
public interface HandlingDao {
    // select all handling instructions for a given material
    @Query("SELECT handlingInstructions.id, handlingInstructions.instructions from handlingInstructions " +
            "INNER JOIN materials_handling ON materials_handling.hid = handlingInstructions.id " +
            "INNER JOIN materials ON materials.id = materials_handling.mid " +
            "WHERE (materials.name = :material);"
    )
    List<Handling> findHandlingByMaterial(String material);
}
