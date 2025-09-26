package org.smartregister.chw.pnc.activity;

import androidx.annotation.NonNull;

import org.smartregister.chw.anc.activity.BaseAncRegisterActivity;
import org.smartregister.chw.pnc.fragment.BasePncRegisterFragment;
import org.smartregister.view.fragment.BaseRegisterFragment;

import timber.log.Timber;

/**
 * Backwards compatible register shell that instantiates the legacy PNC fragment.
 */
public class BasePncRegisterActivity extends BaseAncRegisterActivity {

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new BasePncRegisterFragment();
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
            // Maintain historical behaviour of swallowing unexpected teardown failures.
            Timber.e(e);
        }
    }
}
