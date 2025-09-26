package org.smartregister.chw.pnc.interactor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.util.List;

import org.smartregister.chw.anc.util.AppExecutors;
import org.smartregister.chw.anc.util.VisitUtils;
import org.smartregister.chw.pnc.contract.BasePncMedicalHistoryContract;

/**
 * Fetches and groups PNC visit history on background threads to mirror the legacy behaviour.
 */
public class BasePncMedicalHistoryInteractor implements BasePncMedicalHistoryContract.Interactor {

    private final AppExecutors appExecutors;

    public BasePncMedicalHistoryInteractor() {
        this(new AppExecutors());
    }

    @VisibleForTesting
    BasePncMedicalHistoryInteractor(@NonNull AppExecutors executors) {
        this.appExecutors = executors;
    }

    @Override
    public void getMemberHistory(@NonNull String memberId,
                                 @NonNull Context context,
                                 @NonNull BasePncMedicalHistoryContract.InteractorCallBack callBack) {
        appExecutors.diskIO().execute(() -> {
            List<org.smartregister.chw.anc.domain.Visit> visits =
                    VisitUtils.getVisits(memberId, new String[]{"PNC Home Visit"});
            List<org.smartregister.chw.anc.domain.GroupedVisit> groupedVisits =
                    VisitUtils.getGroupedVisitsByEntity(memberId, "", visits);
            appExecutors.mainThread().execute(() -> callBack.onDataFetched(groupedVisits));
        });
    }
}
