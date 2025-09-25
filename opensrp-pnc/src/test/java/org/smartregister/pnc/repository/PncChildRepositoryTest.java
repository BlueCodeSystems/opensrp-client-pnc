package org.smartregister.pnc.repository;

import android.content.ContentValues;

import net.sqlcipher.database.SQLiteDatabase;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smartregister.pnc.BaseTest;
import org.smartregister.pnc.pojo.PncChild;
import org.smartregister.pnc.utils.PncDbConstants;
import org.smartregister.repository.Repository;

public class PncChildRepositoryTest extends BaseTest {

    private static final String TABLE_NAME = PncDbConstants.Table.PNC_BABY;

    @Mock
    private Repository repository;

    @Mock
    private SQLiteDatabase sqLiteDatabase;


    private PncChildRepository pncChildRepository;
    private PncChild pncChild;

    @Before
    public void setUp() {
        pncChildRepository = Mockito.spy(new PncChildRepository());
        pncChild = Mockito.spy(new PncChild());
    }

    @Test
    public void saveOrUpdate() {
        Mockito.doReturn(repository).when(drishtiApplication).getRepository();
        Mockito.doReturn(sqLiteDatabase).when(pncChildRepository).getWritableDatabase();
        Mockito.when(sqLiteDatabase.insert(Mockito.eq(TABLE_NAME), Mockito.isNull(), Mockito.any(ContentValues.class))).thenReturn(0L);

        Assert.assertTrue(pncChildRepository.saveOrUpdate(pncChild));
    }

    @Test(expected = NotImplementedException.class)
    public void findOne() {
        Mockito.doReturn(repository).when(drishtiApplication).getRepository();
        Mockito.doReturn(sqLiteDatabase).when(pncChildRepository).getReadableDatabase();
        pncChildRepository.findOne(pncChild);
    }

    @Test(expected = NotImplementedException.class)
    public void delete() {
        Mockito.doReturn(repository).when(drishtiApplication).getRepository();
        Mockito.doReturn(sqLiteDatabase).when(pncChildRepository).getReadableDatabase();
        pncChildRepository.delete(pncChild);
    }

    @Test(expected = NotImplementedException.class)
    public void findAll() {
        Mockito.doReturn(repository).when(drishtiApplication).getRepository();
        Mockito.doReturn(sqLiteDatabase).when(pncChildRepository).getReadableDatabase();
        pncChildRepository.findAll();
    }
}
