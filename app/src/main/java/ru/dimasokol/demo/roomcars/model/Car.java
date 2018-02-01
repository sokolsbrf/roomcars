package ru.dimasokol.demo.roomcars.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Конкретный автомобиль
 */
@Entity(tableName = "cars",
        foreignKeys = @ForeignKey(entity = Model.class, parentColumns = "id", childColumns = "model_id"),
        indices = {@Index(value = {"plate"}, unique = true)}
)
public class Car {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "model_id")
    private long mModelId;

    @ColumnInfo(name = "plate")
    private String mNumber;


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getModelId() {
        return mModelId;
    }

    public void setModelId(long modelId) {
        mModelId = modelId;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }
}
