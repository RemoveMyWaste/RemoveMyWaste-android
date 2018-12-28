package com.liambeckman.removemywaste.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liambeckman.removemywaste.centers;
import com.liambeckman.removemywaste.disposal;
import com.liambeckman.removemywaste.materials;

import java.util.List;

@Dao
public interface MaterialsDao {

    @Query("SELECT * FROM materials WHERE (materials.name LIKE :material) " +
            "ORDER BY materials.name;"
    )
    LiveData<List<Materials>> searchMaterials(String material);

    @Query("select * from materials where id = :id")
    Materials loadMaterialById(int id);

    @Query("SELECT * FROM materials")
    List<Materials> findAllMaterials();

}
