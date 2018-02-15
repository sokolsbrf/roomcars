package ru.dimasokol.demo.roomcars;

import android.app.Application;
import android.arch.persistence.room.Room;

import ru.dimasokol.demo.roomcars.database.CarsDatabase;

/**
 * Application
 */
public class CarsApplication extends Application {

    private volatile CarsDatabase mDatabase;

    public CarsDatabase getDatabase() {
        if (mDatabase == null) {
            synchronized (CarsDatabase.class) {
                if (mDatabase == null) {
                    // При создании БД
                    mDatabase = Room.databaseBuilder(this, CarsDatabase.class, "cars")
                            .addMigrations(CarsDatabase.MIGRATION_1_2, CarsDatabase.MIGRATION_2_3)
                            .build();
                }
            }
        }

        return mDatabase;
    }

}
