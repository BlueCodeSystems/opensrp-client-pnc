package org.smartregister.pnc;

import android.os.Build;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.view.activity.DrishtiApplication;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public abstract class BaseTest {

    private AutoCloseable closeable;

    protected DrishtiApplication drishtiApplication = Mockito.mock(DrishtiApplication.class);

    @Before
    public void setUpBase() {
        closeable = MockitoAnnotations.openMocks(this);
        ReflectionHelpers.setStaticField(DrishtiApplication.class, "mInstance", drishtiApplication);
    }

    @After
    public void tearDownBase() throws Exception {
        ReflectionHelpers.setStaticField(DrishtiApplication.class, "mInstance", null);
        if (closeable != null) {
            closeable.close();
        }
    }
}
