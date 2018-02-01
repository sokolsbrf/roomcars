package ru.dimasokol.demo.roomcars.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Производитель автомобилей
 */
@Entity(tableName = "vendors", indices = {@Index(value = {"mBrandName"}, unique = true)})
public class Vendor {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo
    private String mBrandName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getBrandName() {
        return mBrandName;
    }

    public void setBrandName(String brandName) {
        mBrandName = brandName;
    }
}
