package org.smartregister.chw.pnc.interactor;

import static android.view.View.VISIBLE;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.smartregister.chw.anc.contract.BaseAncMedicalHistoryContract;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.interactor.BaseAncMemberProfileInteractor;
import org.smartregister.chw.pnc.PncLibrary;
import org.smartregister.chw.pnc.contract.BasePncMemberProfileContract;
import org.smartregister.chw.pnc.util.PncUtil;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.util.Utils;
import org.smartregister.pnc.R;

import timber.log.Timber;

/**
 * Supplies the legacy CHW PNC profile information expected by downstream apps.
 */
public class BasePncMemberProfileInteractor extends BaseAncMemberProfileInteractor
        implements BasePncMemberProfileContract.Interactor {

    protected BaseAncMedicalHistoryContract.InteractorCallBack interactorCallBack;

    public String getPncDay(@NonNull String motherBaseEntityId) {
        String deliveryDate = PncLibrary.getInstance().profileRepository().getDeliveryDate(motherBaseEntityId);
        if (deliveryDate == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime delivery = formatter.parseDateTime(deliveryDate);
        return String.valueOf(Days.daysBetween(delivery, DateTime.now()).getDays());
    }

    @Nullable
    public String getLastVisitDate(@NonNull String motherBaseEntityId) {
        Long lastVisit = PncLibrary.getInstance().profileRepository().getLastVisit(motherBaseEntityId);
        if (lastVisit == null) {
            return null;
        }
        Date date = new Date(lastVisit);
        return new SimpleDateFormat("dd MMM", Locale.getDefault()).format(date);
    }

    public String getPncMotherNameDetails(@NonNull MemberObject memberObject,
                                          @NonNull TextView nameView,
                                          @NonNull CircleImageView profileView) {
        List<CommonPersonObjectClient> children = pncChildrenUnder29Days(memberObject.getBaseEntityId());
        String displayName = memberObject.getMemberName();

        nameView.setText(displayName);
        nameView.setSingleLine(false);
        nameView.setVisibility(VISIBLE);
        profileView.setImageResource(org.smartregister.chw.opensrp_chw_anc.R.mipmap.ic_member);

        if (children == null || children.isEmpty()) {
            return displayName;
        }

        for (CommonPersonObjectClient child : children) {
            try {
                String firstName = (String) child.getColumnmaps().get("first_name");
                String middleName = (String) child.getColumnmaps().get("middle_name");
                String lastName = (String) child.getColumnmaps().get("last_name");
                String dob = (String) child.getColumnmaps().get("dob");
                String genderValue = (String) child.getColumnmaps().get("gender");
                char gender = StringUtils.isNotBlank(genderValue) ? genderValue.charAt(0) : 'F';

                String childAgeInDays = String.valueOf(PncUtil.getDaysDifference(dob));
                String childDetails = childNameDetails(firstName, middleName, lastName, childAgeInDays, gender);
                if (StringUtils.isNotBlank(childDetails)) {
                    nameView.append(" +\n" + childDetails);
                    profileView.setImageResource(R.drawable.pnc_less_twenty_nine_days);
                    if (children.size() == 1) {
                        profileView.setMaxWidth(10);
                        profileView.setMaxHeight(10);
                        profileView.setBorderWidth(14);
                        profileView.setBorderColor(gender == 'M'
                                ? PncLibrary.getInstance().context().getColorResource(R.color.light_blue)
                                : PncLibrary.getInstance().context().getColorResource(R.color.light_pink));
                    }
                }
            } catch (Exception e) {
                Timber.e(e);
            }
        }

        return displayName;
    }

    public List<CommonPersonObjectClient> pncChildrenUnder29Days(@NonNull String motherBaseEntityId) {
        return PncLibrary.getInstance().profileRepository().getChildrenLessThan29DaysOld(motherBaseEntityId);
    }

    private String childNameDetails(@Nullable String firstName,
                                    @Nullable String middleName,
                                    @Nullable String surName,
                                    @NonNull String ageInDays,
                                    char gender) {
        String childName = Utils.getName(firstName, Utils.getName(middleName, surName));
        if (StringUtils.isBlank(childName)) {
            return null;
        }
        String daySuffix = PncLibrary.getInstance().context().getStringResource(R.string.pnc_day_count);
        return childName + ", " + ageInDays + daySuffix + ", " + gender;
    }
}
