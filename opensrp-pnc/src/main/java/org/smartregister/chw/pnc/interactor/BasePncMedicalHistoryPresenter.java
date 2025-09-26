package org.smartregister.chw.pnc.interactor;

import java.lang.ref.WeakReference;
import java.util.List;

import org.smartregister.chw.anc.domain.GroupedVisit;
import org.smartregister.chw.pnc.contract.BasePncMedicalHistoryContract;

/**
 * Presenter bridge that mirrors the original CHW MVP structure for medical history rendering.
 */
public class BasePncMedicalHistoryPresenter implements
        BasePncMedicalHistoryContract.Presenter,
        BasePncMedicalHistoryContract.InteractorCallBack {

    private final BasePncMedicalHistoryContract.Interactor interactor;
    private final WeakReference<BasePncMedicalHistoryContract.View> viewRef;
    private final String memberId;

    public BasePncMedicalHistoryPresenter(BasePncMedicalHistoryContract.Interactor interactor,
                                          BasePncMedicalHistoryContract.View view,
                                          String memberId) {
        this.interactor = interactor;
        this.viewRef = new WeakReference<>(view);
        this.memberId = memberId;
        initialize();
    }

    @Override
    public void initialize() {
        BasePncMedicalHistoryContract.View view = getView();
        if (view != null) {
            interactor.getMemberHistory(memberId, view.getViewContext(), this);
        }
    }

    @Override
    public BasePncMedicalHistoryContract.View getView() {
        return viewRef.get();
    }

    @Override
    public void onDataFetched(List<GroupedVisit> groupedVisits) {
        BasePncMedicalHistoryContract.View view = getView();
        if (view != null) {
            view.onGroupedDataReceived(groupedVisits);
        }
    }
}
