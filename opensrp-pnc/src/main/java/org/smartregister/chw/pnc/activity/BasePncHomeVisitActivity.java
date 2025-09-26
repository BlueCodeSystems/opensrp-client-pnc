package org.smartregister.chw.pnc.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import java.text.MessageFormat;

import org.smartregister.chw.anc.activity.BaseAncHomeVisitActivity;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.pnc.R;

/**
 * Legacy entry point that keeps the historical CHW PNC home-visit workflow alive for consumers.
 */
public class BasePncHomeVisitActivity extends BaseAncHomeVisitActivity {

    public static void startMe(@NonNull Activity activity,
                               @NonNull MemberObject memberObject,
                               boolean isEditMode) {
        Intent intent = new Intent(activity, BasePncHomeVisitActivity.class);
        intent.putExtra("MemberObject", memberObject);
        intent.putExtra("editMode", isEditMode);
        intent.putExtra("BASE_ENTITY_ID", memberObject.getBaseEntityId());
        activity.startActivity(intent);
    }

    public static void startMe(@NonNull Activity activity,
                               @NonNull String baseEntityId,
                               boolean isEditMode) {
        Intent intent = new Intent(activity, BasePncHomeVisitActivity.class);
        intent.putExtra("BASE_ENTITY_ID", baseEntityId);
        intent.putExtra("editMode", isEditMode);
        activity.startActivity(intent);
    }

    @Override
    public void redrawHeader(@NonNull MemberObject memberObject) {
        String title = MessageFormat.format("{0}, {1} · {2}",
                memberObject.getFullName(),
                memberObject.getAge(),
                getString(R.string.pnc_visit));
        tvTitle.setText(title);
    }
}
