package org.smartregister.chw.pnc.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Set;

import org.smartregister.chw.anc.contract.BaseAncRegisterFragmentContract;
import org.smartregister.chw.anc.fragment.BaseAncRegisterFragment;
import org.smartregister.chw.anc.model.BaseAncRegisterFragmentModel;
import org.smartregister.chw.pnc.activity.BasePncHomeVisitActivity;
import org.smartregister.chw.pnc.activity.BasePncMemberProfileActivity;
import org.smartregister.chw.pnc.presenter.BasePncRegisterFragmentPresenter;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.cursoradapter.RecyclerViewProvider;
import org.smartregister.pnc.R;
import org.smartregister.pnc.provider.PncRegisterProvider;
import org.smartregister.view.customcontrols.CustomFontTextView;

/**
 * Legacy fragment that mirrors the original CHW PNC register UX while leveraging the modern ANC
 * register scaffolding.
 */
public class BasePncRegisterFragment extends BaseAncRegisterFragment {

    @Override
    public void initializeAdapter(@NonNull Set<org.smartregister.configurableviews.model.View> visibleColumns) {
        Context context = getActivity();
        RecyclerViewProvider provider = new PncRegisterProvider(
                context,
                registerActionHandler,
                paginationViewHandler);
        clientAdapter = new RecyclerViewPaginatedAdapter(
                null,
                provider,
                this.context().commonrepository(tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }

    @Override
    public void setupViews(@NonNull View view) {
        super.setupViews(view);
        CustomFontTextView titleView = view.findViewById(org.smartregister.chw.opensrp_chw_anc.R.id.txt_title_label);
        if (titleView != null) {
            titleView.setText(getString(R.string.pnc));
        }
    }

    @Override
    protected void initializePresenter() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        presenter = new BasePncRegisterFragmentPresenter(
                this,
                new BaseAncRegisterFragmentModel(),
                null);
    }

    @Override
    protected String getDefaultSortQuery() {
        return presenter().getDefaultSortQuery();
    }

    @Override
    protected void openProfile(@NonNull CommonPersonObjectClient client) {
        BasePncMemberProfileActivity.startMe(getActivity(), client.getCaseId());
    }

    @Override
    protected void openHomeVisit(@NonNull CommonPersonObjectClient client) {
        BasePncHomeVisitActivity.startMe(getActivity(), client.getCaseId(), false);
    }
}
