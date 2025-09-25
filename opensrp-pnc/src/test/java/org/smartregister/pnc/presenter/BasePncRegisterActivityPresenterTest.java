package org.smartregister.pnc.presenter;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.pnc.PncLibrary;
import org.smartregister.pnc.contract.PncRegisterActivityContract;

import java.lang.ref.WeakReference;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class BasePncRegisterActivityPresenterTest {

    @Mock
    private PncLibrary pncLib;

    @Before
    public void setUp() {
        ReflectionHelpers.setStaticField(PncLibrary.class, "instance", pncLib);
    }

    @After
    public void tearDown() {
        ReflectionHelpers.setStaticField(PncLibrary.class, "instance", null);
    }

    @Test
    public void startFormShouldPassEntityTableAndBaseEntityIdToActivity() throws JSONException {
        PncRegisterActivityContract.View view = Mockito.mock(PncRegisterActivityContract.View.class);
        PncRegisterActivityContract.Model model = Mockito.mock(PncRegisterActivityContract.Model.class);

        BasePncRegisterActivityPresenter presenter = new PncRegisterActivityPresenter(view, model);

        Mockito.doReturn(new JSONObject()).when(model).getFormAsJson(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.nullable(HashMap.class));

        ReflectionHelpers.setField(presenter, "viewReference", new WeakReference<>(view));
        presenter.setModel(model);

        presenter.startForm("check_in.json", "90923-dsfds", "meta", "location-id", null, "ec_child");

        Mockito.verify(view, Mockito.times(1))
                .startFormActivityFromFormJson(Mockito.anyString(), Mockito.any(JSONObject.class), Mockito.any(HashMap.class));
    }
}
