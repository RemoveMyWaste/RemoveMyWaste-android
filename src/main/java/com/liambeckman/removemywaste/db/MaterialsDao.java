package com.liambeckman.removemywaste.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MaterialsDao {

    @Query("SELECT * FROM materials WHERE (materials.name LIKE '%' || :material || '%') " +
            "ORDER BY materials.name;"
    )
    List<Materials> searchMaterials(String material);

    @Query("select * from materials where id = :id")
    Materials loadMaterialById(int id);

    @Query("SELECT * FROM materials ORDER BY materials.name")
    List<Materials> findAllMaterials();

    @Query("SELECT materials.* FROM materials " +
            "INNER JOIN centers_materials ON centers_materials.mid = materials.id " +
            "INNER JOIN centers on centers.id = centers_materials.cid " +
            "WHERE centers.id = :id")
    List<Materials> searchCentersMaterials(int id);

}
