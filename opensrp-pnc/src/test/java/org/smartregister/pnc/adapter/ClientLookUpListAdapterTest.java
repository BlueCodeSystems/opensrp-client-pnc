package org.smartregister.pnc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.pnc.utils.PncDbConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.util.ReflectionHelpers.setStaticField;

@RunWith(MockitoJUnitRunner.class)
public class ClientLookUpListAdapterTest {

    private ClientLookUpListAdapter adapter;

    @Mock
    private List<CommonPersonObject> data;

    @Mock
    private Context context;

    @Mock
    private LayoutInflater mInflater;

    private ClientLookUpListAdapter.ClickListener clickListener;

    @Before
    public void setUp() {
        adapter = new ClientLookUpListAdapter(data, context);
        setStaticField(ClientLookUpListAdapter.class, "clickListener", null);
        clickListener = Mockito.mock(ClientLookUpListAdapter.ClickListener.class);
        setStaticField(ClientLookUpListAdapter.class, "clickListener", clickListener);
        org.robolectric.util.ReflectionHelpers.setField(adapter, "mInflater", mInflater);
    }

    @Test
    public void onCreateViewHolderShouldReturnMyViewHolder() {
        View itemView = mock(View.class);
        ViewGroup parent = mock(ViewGroup.class);
        ClientLookUpListAdapter.MyViewHolder viewHolder = mock(ClientLookUpListAdapter.MyViewHolder.class);

        doReturn(itemView).when(mInflater).inflate(anyInt(), any(ViewGroup.class), anyBoolean());

        int viewType = -1;

        assertThat(viewHolder, instanceOf(adapter.onCreateViewHolder(parent, viewType).getClass()));
    }

    @Test
    public void onBindViewHolderShouldVerifyImplementation() {
        TextView txtName = mock(TextView.class);
        View itemView = mock(View.class);
        TextView txtDetails = mock(TextView.class);

        ClientLookUpListAdapter.MyViewHolder viewHolder = mock(ClientLookUpListAdapter.MyViewHolder.class);
        org.robolectric.util.ReflectionHelpers.setField(viewHolder, "txtName", txtName);
        org.robolectric.util.ReflectionHelpers.setField(viewHolder, "itemView", itemView);
        org.robolectric.util.ReflectionHelpers.setField(viewHolder, "txtDetails", txtDetails);

        String firstName = "First Name";
        String lastName = "Last Name";
        String openSrpId = "AFFFFFFF000000000000000000034";

        String caseId = "3sf-3-323ef3-fdsf3";
        String relationalId = "3223-s-df-3";
        String type = "unknown";
        Map<String, String> columnsMap = new HashMap<>();
        columnsMap.put(PncDbConstants.Column.Client.FIRST_NAME, firstName);
        columnsMap.put(PncDbConstants.Column.Client.LAST_NAME, lastName);
        columnsMap.put(PncDbConstants.KEY.OPENSRP_ID, openSrpId);

        CommonPersonObject commonPersonObject = Mockito.spy(new CommonPersonObject(caseId, relationalId, columnsMap, type));
        commonPersonObject.setColumnmaps(columnsMap);

        when(data.get(anyInt())).thenReturn(commonPersonObject);
        doReturn("Opensrp Id").when(context).getString(anyInt());

        int position = 0;
        adapter.onBindViewHolder(viewHolder, position);

        String fullName = firstName + " " + lastName;
        String details = "Opensrp Id - " + openSrpId;
        verify(txtName, Mockito.times(1)).setText(fullName);
        verify(txtDetails, Mockito.times(1)).setText(details);
    }

    @Test
    public void getItemCountShouldReturnValidSize() {
        int size = 1;
        when(data.size()).thenReturn(size);
        assertEquals(size, adapter.getItemCount());
    }

    @Test
    public void onClickShouldVerifyListener() {
        View view = mock(View.class);
        ClientLookUpListAdapter.MyViewHolder viewHolder = new ClientLookUpListAdapter.MyViewHolder(view);
        viewHolder.onClick(view);

        verify(clickListener, Mockito.times(1)).onItemClick(view);
    }
}
