package com.liambeckman.removemywaste.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liambeckman.removemywaste.centers;
import com.liambeckman.removemywaste.disposal;
import com.liambeckman.removemywaste.materials;

import java.util.List;

@Dao
public interface CentersDao {
    // select all centers that accept a given material
    /*
    @Query("SELECT centers.name, centers.street_number, centers.street_direction, centers.street_name, centers.street_type, centers.city, centers.state, centers.zip " +
            "FROM centers_materials INNER JOIN centers ON centers.id = centers_materials.CID " +
            "INNER JOIN materials ON materials.id = centers_materials.MID WHERE (materials.name = :material) " +
            "ORDER BY centers.name;"
    )
    LiveData<List<Centers>> findCentersByMaterial(String material);
    */


    @Query("SELECT * FROM centers WHERE centers.name LIKE '%' || :center || '%'" +
            "ORDER BY centers.name;"
    )
    List<Centers> searchCenters(String center);


    @Query("select * from centers where id = :id")
    Centers loadCentersById(int id);

    @Query("SELECT * FROM centers ORDER BY centers.name")
    List<Centers> findAllCenters();


}
