package org.smartregister.chw.pnc.presenter;

import java.util.List;

import org.smartregister.chw.anc.contract.BaseAncRegisterFragmentContract;
import org.smartregister.chw.anc.presenter.BaseAncRegisterFragmentPresenter;
import org.smartregister.configurableviews.model.Field;

/**
 * Presenter shell retained for binary compatibility with CHW PNC register screens.
 */
public class BasePncRegisterFragmentPresenter extends BaseAncRegisterFragmentPresenter {

    public BasePncRegisterFragmentPresenter(BaseAncRegisterFragmentContract.View view,
                                            BaseAncRegisterFragmentContract.Model model,
                                            String viewConfigurationIdentifier) {
        super(view, model, viewConfigurationIdentifier);
    }

    public void updateSortAndFilter(List<Field> filterList, Field sortField) {
        // Intentionally left blank – legacy behaviour did not apply extra filtering here.
    }

    public String getMainCondition() {
        return "";
    }

    public String getDefaultSortQuery() {
        return "ec_pregnancy_outcome.last_interacted_with DESC ";
    }

    public String getMainTable() {
        return "ec_pregnancy_outcome";
    }

    public String getDueFilterCondition() {
        return "";
    }
}
