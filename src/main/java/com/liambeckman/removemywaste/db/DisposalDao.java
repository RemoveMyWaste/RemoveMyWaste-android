package com.liambeckman.removemywaste.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liambeckman.removemywaste.disposal;

import java.util.List;

@Dao
public interface DisposalDao {

    // select all disposal instructions for a given material
    /*
    @Query("SELECT disposal.instructions from disposal " +
            "INNER JOIN materials_disposal ON materials_disposal.did = disposal.id " +
            "INNER JOIN materials ON materials.id = materials_disposal.mid " +
            "WHERE (materials.name = :material);"
    )
    LiveData<List<Disposal>> findDisposalByMaterial(String material);
    */

}
