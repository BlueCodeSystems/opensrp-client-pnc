package org.smartregister.chw.pnc.contract;

import android.content.Context;
import android.view.View;

import java.util.List;

import org.smartregister.chw.anc.domain.GroupedVisit;

/**
 * Compatibility contract describing the CHW PNC medical history MVP bindings.
 */
public interface BasePncMedicalHistoryContract {

    interface Interactor {
        void getMemberHistory(String memberId, Context context, InteractorCallBack callBack);
    }

    interface InteractorCallBack {
        void onDataFetched(List<GroupedVisit> groupedVisits);
    }

    interface Presenter {
        void initialize();

        BasePncMedicalHistoryContract.View getView();
    }

    interface View {
        void initializePncPresenter();

        Presenter getPncPresenter();

        void onGroupedDataReceived(List<GroupedVisit> groupedVisits);

        Context getViewContext();

        void displayLoadingState(boolean showLoading);

        android.view.View renderMedicalHistoryView(List<GroupedVisit> groupedVisits);
    }
}
