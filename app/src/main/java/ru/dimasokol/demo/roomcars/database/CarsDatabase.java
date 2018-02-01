package ru.dimasokol.demo.roomcars.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import ru.dimasokol.demo.roomcars.model.Car;
import ru.dimasokol.demo.roomcars.model.Model;
import ru.dimasokol.demo.roomcars.model.Vendor;

/**
 * База данных
 */
@Database(entities = {Vendor.class, Model.class, Car.class}, version = 3)
public abstract class CarsDatabase extends RoomDatabase {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE INDEX `index_models_model` ON `models` (`model`)");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE UNIQUE INDEX `index_vendors_mBrandName` ON `vendors` (`mBrandName`)");
        }
    };

    public abstract CarsDao getDao();

}
