package org.smartregister.chw.pnc.activity;

import static android.view.View.GONE;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.util.Date;

import org.smartregister.chw.anc.activity.BaseAncMemberProfileActivity;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.util.NCUtils;
import org.smartregister.chw.pnc.interactor.BasePncMemberProfileInteractor;
import org.smartregister.pnc.R;
import org.smartregister.util.Utils;
import org.smartregister.view.customcontrols.CustomFontTextView;

/**
 * Compatibility activity that preserves the historic CHW PNC entry points while delegating to the
 * current ANC foundation classes. Downstream CHW applications still extend this type directly.
 */
public class BasePncMemberProfileActivity extends BaseAncMemberProfileActivity {

    private static final int REQUEST_CODE_PROFILE = 3344;

    private final BasePncMemberProfileInteractor interactor = new BasePncMemberProfileInteractor();

    public static void startMe(Activity activity, String baseEntityId) {
        Intent intent = new Intent(activity, BasePncMemberProfileActivity.class);
        intent.putExtra("BASE_ENTITY_ID", baseEntityId);
        activity.startActivityForResult(intent, REQUEST_CODE_PROFILE);
    }

    @Override
    protected void setupViews() {
        super.setupViews();

        CustomFontTextView toolbarTitle = findViewById(org.smartregister.R.id.txt_title_label);
        String title = TextUtils.isEmpty(getTitleViewText())
                ? getString(R.string.return_to_all_pnc_women)
                : getTitleViewText();
        toolbarTitle.setText(title);

        record_reccuringvisit_done_bar.setVisibility(GONE);
        textViewAncVisitNot.setVisibility(GONE);
    }

    @Override
    public void setMemberName(@Nullable String memberName) {
        interactor.getPncMotherNameDetails(memberObject, text_view_anc_member_name, imageView);
    }

    @Override
    public void setMemberGA(@Nullable String ga) {
        String pncDay = interactor.getPncDay(memberObject.getBaseEntityId());
        if (pncDay != null) {
            text_view_ga.setText(Utils.getName(getString(R.string.pnc_day), pncDay));
        }
    }

    @Override
    public void setProfileImage(String baseEntityId, String familyBaseEntityId) {
        String pncDay = interactor.getPncDay(memberObject.getBaseEntityId());
        ImageView profileView = imageView;
        if (TextUtils.isEmpty(pncDay)) {
            imageRenderHelper.refreshProfileImage(baseEntityId, profileView, R.drawable.pnc_less_twenty_nine_days);
            return;
        }

        try {
            int day = Integer.parseInt(pncDay);
            if (day >= 29) {
                imageRenderHelper.refreshProfileImage(baseEntityId, profileView,
                        NCUtils.getMemberProfileImageResourceIDentifier("pnc"));
            } else {
                imageRenderHelper.refreshProfileImage(baseEntityId, profileView, R.drawable.pnc_less_twenty_nine_days);
            }
        } catch (NumberFormatException e) {
            imageRenderHelper.refreshProfileImage(baseEntityId, profileView, R.drawable.pnc_less_twenty_nine_days);
        }
    }

    @Override
    protected String getProfileType() {
        return "pnc";
    }

    @Override
    public void setRecordVisitTitle(@Nullable String title) {
        textview_record_anc_visit.setText(getString(R.string.record_pnc_visit));
    }

    @Override
    public void openMedicalHistory() {
        BasePncMedicalHistoryActivity.startMe(this, memberObject);
    }

    @Override
    public void setLastVisit(@Nullable Date lastVisit) {
        view_last_visit_row.setVisibility(android.view.View.VISIBLE);
        String lastVisitDate = interactor.getLastVisitDate(memberObject.getBaseEntityId());
        if (lastVisitDate != null) {
            rlLastVisit.setVisibility(android.view.View.VISIBLE);
            tvLastVisitDate.setText(MessageFormat.format(getString(R.string.pnc_last_visit_text), lastVisitDate));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ensure toolbar mirrors compatibility title when returning to screen
        CustomFontTextView toolbarTitle = findViewById(org.smartregister.R.id.txt_title_label);
        if (toolbarTitle != null) {
            toolbarTitle.setText(getString(R.string.return_to_all_pnc_women));
        }
    }
}
