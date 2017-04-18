package com.xix.cleanMvpArchitecture.data.store;

import com.xix.cleanMvpArchitecture.data.cache.DataCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by xix on 4/17/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DiskDataStoreTest {
    private DiskDataStore mDiskDataStore;
    @Mock private DataCache mDataCache;

    @Before public void setUp() throws Exception {
        mDiskDataStore = new DiskDataStore(mDataCache);
    }

    @Test public void testGetItems() throws Exception {
        mDiskDataStore.getItems();
        Mockito.verify(mDataCache).getItemList();
    }
}