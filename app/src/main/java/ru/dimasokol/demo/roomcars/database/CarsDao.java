package ru.dimasokol.demo.roomcars.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.dimasokol.demo.roomcars.model.Model;
import ru.dimasokol.demo.roomcars.model.Vendor;

/**
 * DAO
 */
@Dao
public interface CarsDao {

    @Query("SELECT * from vendors ORDER BY mBrandName")
    Flowable<List<Vendor>> getVendors();

    @Query("SELECT * FROM vendors WHERE id = :vendorId")
    Flowable<Vendor> getVendorById(long vendorId);

    @Query("SELECT * FROM models WHERE vendor_id = :vendorId ORDER BY model")
    Flowable<List<Model>> getModelsByVendorId(long vendorId);

    @Insert
    void insert(Vendor... vendors);

    @Insert
    void insert(Model... models);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long insertSingle(Vendor vendor);

    @Delete
    void delete(Vendor vendor);
}
