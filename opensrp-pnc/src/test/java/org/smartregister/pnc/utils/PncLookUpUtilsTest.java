package org.smartregister.pnc.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.robolectric.util.ReflectionHelpers;
import org.robolectric.util.ReflectionHelpers.ClassParameter;
import org.smartregister.Context;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.pnc.BuildConfig;
import org.smartregister.pnc.PncLibrary;
import org.smartregister.pnc.config.PncConfiguration;
import org.smartregister.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class PncLookUpUtilsTest {

    @Test
    public void testGetMainConditionStringWhenEntityMapIsEmpty() {
        Map<String, String> entityMap = new HashMap<>();
        String result = ReflectionHelpers.callStaticMethod(PncLookUpUtils.class, "getMainConditionString",
                ClassParameter.from(Map.class, entityMap));
        Assert.assertEquals("", result);
    }

    @Test
    public void testGetMainConditionStringWhenEntityMapHasValues() {
        Map<String, String> entityMap = new HashMap<>();
        entityMap.put("first_name", "");
        entityMap.put("last_name", "");
        entityMap.put("bht_mid", "");
        entityMap.put("national_id", "");
        String result = ReflectionHelpers.callStaticMethod(PncLookUpUtils.class, "getMainConditionString",
                ClassParameter.from(Map.class, entityMap));
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testClientLookUpWhenContextIsNull() {
        Map<String, String> entityLookUp = new HashMap<>();
        List<CommonPersonObject> result = PncLookUpUtils.clientLookUp(null, entityLookUp);
        Assert.assertEquals(new ArrayList<>(), result);
    }

    @Test
    public void testClientLookUpWhenMapIsEmpty() {
        Map<String, String> entityLookUp = new HashMap<>();
        List<CommonPersonObject> result = PncLookUpUtils.clientLookUp(mock(Context.class), entityLookUp);
        Assert.assertEquals(new ArrayList<>(), result);
    }

    @Test
    public void testClientLookUpWhenMapIsNotEmptyAndContextIsNotNullWithTable() {
        PncLibrary.init(mock(Context.class), mock(Repository.class), mock(PncConfiguration.class),
                BuildConfig.VERSION_CODE, 1);
        Map<String, String> entityLookUp = new HashMap<>();
        entityLookUp.put("first_name", "");
        List<CommonPersonObject> result = PncLookUpUtils.clientLookUp(mock(Context.class), entityLookUp);
        Assert.assertEquals(new ArrayList<>(), result);
    }

    @After
    public void tearDown() {
        ReflectionHelpers.setStaticField(PncLibrary.class, "instance", null);
    }
}
