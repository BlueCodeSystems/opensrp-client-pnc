package org.smartregister.chw.pnc.repository;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.chw.pnc.util.PncUtil;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.repository.BaseRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

/**
 * Compatibility repository that exposes the subset of queries the legacy CHW modules relied on.
 */
public class ProfileRepository extends BaseRepository {

    private static final String TABLE_CHILD = "ec_child";
    private static final String TABLE_PREGNANCY_OUTCOME = "ec_pregnancy_outcome";
    private static final String COL_MOTHER_ENTITY_ID = "mother_entity_id";
    private static final String COL_DELIVERY_DATE = "delivery_date";
    private static final String COL_BASE_ENTITY_ID = "base_entity_id";
    private static final String COLLATE_NOCASE = " COLLATE NOCASE";

    @NonNull
    public List<CommonPersonObjectClient> getChildrenLessThan29DaysOld(@NonNull String motherBaseEntityId) {
        List<CommonPersonObjectClient> results = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        if (database == null) {
            return results;
        }

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(
                    "SELECT * FROM " + TABLE_CHILD + " WHERE " + COL_MOTHER_ENTITY_ID + "=? AND is_closed = 0 ORDER BY first_name ASC",
                    new String[]{motherBaseEntityId});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String dob = cursor.getString(cursor.getColumnIndex("dob"));
                    if (dob != null && PncUtil.getDaysDifference(dob) < 29) {
                        results.add(getChildMember(cursor));
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return results;
    }

    @Nullable
    public String getDeliveryDate(@NonNull String motherBaseEntityId) {
        SQLiteDatabase database = getReadableDatabase();
        if (database == null) {
            return null;
        }

        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_PREGNANCY_OUTCOME,
                    new String[]{COL_DELIVERY_DATE},
                    COL_BASE_ENTITY_ID + " = ?" + COLLATE_NOCASE,
                    new String[]{motherBaseEntityId},
                    null,
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(COL_DELIVERY_DATE));
            }
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    @Nullable
    public Long getLastVisit(@NonNull String motherBaseEntityId) {
        SQLiteDatabase database = getReadableDatabase();
        if (database == null) {
            return null;
        }

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(
                    "SELECT visit_date FROM visits WHERE visit_type = ? AND base_entity_id = ?",
                    new String[]{"PNC Home Visit", motherBaseEntityId});
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex("visit_date"));
            }
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private CommonPersonObjectClient getChildMember(@NonNull Cursor cursor) {
        String[] columnNames = cursor.getColumnNames();
        HashMap<String, String> details = new HashMap<>();
        for (String columnName : columnNames) {
            details.put(columnName, cursor.getString(cursor.getColumnIndex(columnName)));
        }
        CommonPersonObjectClient client = new CommonPersonObjectClient("", details, "");
        client.setColumnmaps(details);
        client.setCaseId(cursor.getString(cursor.getColumnIndex(COL_BASE_ENTITY_ID)));
        return client;
    }
}
