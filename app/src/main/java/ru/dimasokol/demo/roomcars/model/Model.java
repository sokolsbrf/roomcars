package ru.dimasokol.demo.roomcars.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Модель автомобиля от производителя
 */
@Entity(tableName = "models",
        foreignKeys = @ForeignKey(
                             entity = Vendor.class,
                             parentColumns = "id",
                             childColumns = "vendor_id",
                             onDelete = CASCADE),
        indices = {@Index({"model"})})
public class Model {

    public Model(long vendorId , String name) {
        mVendorId = vendorId;
        mName = name;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "vendor_id")
    private long mVendorId;

    @ColumnInfo(name = "model")
    private String mName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getVendorId() {
        return mVendorId;
    }

    public void setVendorId(long vendorId) {
        mVendorId = vendorId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
