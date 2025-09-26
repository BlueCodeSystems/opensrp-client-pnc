package org.smartregister.chw.pnc.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.smartregister.chw.pnc.repository.PncCloseDateRepository;
import org.smartregister.clientandeventmodel.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Utility helpers retained for consumers that still depend on the CHW PNC convenience methods.
 */
public final class PncUtil {

    private PncUtil() {
        // no-op
    }

    public static int getDaysDifference(@Nullable String eventDateStr) {
        Date reference = getDate(eventDateStr);
        if (reference == null) {
            return 0;
        }
        return Days.daysBetween(new DateTime(reference), DateTime.now()).getDays();
    }

    @Nullable
    private static Date getDate(@Nullable String eventDateStr) {
        if (StringUtils.isBlank(eventDateStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ", Locale.US).parse(eventDateStr);
        } catch (ParseException ignored) {
            // try the next format
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(eventDateStr);
        } catch (ParseException ignored) {
            // try final fallback
        }
        try {
            return DateUtil.parseDate(eventDateStr);
        } catch (ParseException e) {
            Timber.e(e, "Failed to parse PNC date %s", eventDateStr);
            return null;
        }
    }

    public static void updatePregancyOutcome(@Nullable Integer numberOfDays,
                                             @NonNull PncCloseDateRepository repository) {
        if (numberOfDays == null) {
            return;
        }
        repository.closeOldPNCRecords(numberOfDays);
    }
}
