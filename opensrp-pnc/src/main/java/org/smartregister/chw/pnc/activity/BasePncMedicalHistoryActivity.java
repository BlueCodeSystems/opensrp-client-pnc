package org.smartregister.chw.pnc.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import java.util.List;

import org.smartregister.chw.anc.activity.BaseAncMedicalHistoryActivity;
import org.smartregister.chw.anc.domain.GroupedVisit;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.pnc.contract.BasePncMedicalHistoryContract;
import org.smartregister.chw.pnc.interactor.BasePncMedicalHistoryInteractor;
import org.smartregister.chw.pnc.interactor.BasePncMedicalHistoryPresenter;

/**
 * Compatibility medical history screen that wires the CHW presenter/interactor back into ANC.
 */
public class BasePncMedicalHistoryActivity extends BaseAncMedicalHistoryActivity
        implements BasePncMedicalHistoryContract.View {

    protected BasePncMedicalHistoryContract.Presenter pncPresenter;

    public static void startMe(@NonNull Activity activity, @NonNull MemberObject memberObject) {
        Intent intent = new Intent(activity, BasePncMedicalHistoryActivity.class);
        intent.putExtra("MemberObject", memberObject);
        activity.startActivity(intent);
    }

    @Override
    public void initializePncPresenter() {
        pncPresenter = new BasePncMedicalHistoryPresenter(
                new BasePncMedicalHistoryInteractor(),
                this,
                memberObject.getBaseEntityId());
    }

    @Override
    public BasePncMedicalHistoryContract.Presenter getPncPresenter() {
        return pncPresenter;
    }

    @Override
    public void onGroupedDataReceived(@NonNull List<GroupedVisit> groupedVisits) {
        android.view.View historyView = renderMedicalHistoryView(groupedVisits);
        linearLayout.addView(historyView, 0);
    }

    @Override
    public android.view.View renderMedicalHistoryView(@NonNull List<GroupedVisit> groupedVisits) {
        LayoutInflater inflater = getLayoutInflater();
        return inflater.inflate(org.smartregister.chw.opensrp_chw_anc.R.layout.medical_history_details,
                linearLayout, false);
    }
}
