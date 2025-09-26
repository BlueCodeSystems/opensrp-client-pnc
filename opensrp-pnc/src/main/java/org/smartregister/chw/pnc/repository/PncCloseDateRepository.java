package org.smartregister.chw.pnc.repository;

import android.content.ContentValues;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.repository.BaseRepository;

import java.util.Date;

/**
 * Data access helper that mirrors the legacy behaviour for closing PNC records after a given
 * duration.
 */
public class PncCloseDateRepository extends BaseRepository {

    public void closeOldPNCRecords(int durationInDays) {
        SQLiteDatabase database = getWritableDatabase();
        if (database == null) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put("is_closed", 1);
        values.put("last_interacted_with", new Date().getTime());

        String where = " cast(julianday(datetime('now')) - julianday(datetime(substr(delivery_date, 7,4)  || '-' || substr(delivery_date, 4,2) || '-' || substr(delivery_date, 1,2))) as integer) >= ? ";
        String[] whereArgs = new String[]{String.valueOf(durationInDays)};
        database.update("ec_pregnancy_outcome", values, where, whereArgs);
    }
}
