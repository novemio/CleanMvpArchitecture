package com.xix.cleanMvpArchitecture.data.store;

import android.test.mock.MockContext;
import com.xix.cleanMvpArchitecture.data.cache.DataCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by xix on 4/17/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataStoreFactoryTest {

    private MockContext mContext;
    @Mock private DataCache mDataCache;
    private DataStoreFactory mDataStoreFactory;

    @Before public void setUp() throws Exception {
        mContext = new MockContext();
        mDataStoreFactory = new DataStoreFactory(mContext, mDataCache);
    }

    @Test public void testGetItemsFromDisk() throws Exception {
        BDDMockito.given(mDataCache.isItemListCached()).willReturn(true);
        BDDMockito.given(mDataCache.isExpired()).willReturn(false);
        mDataStoreFactory.getItems();
        Mockito.verify(mDataCache).getItemList();
    }


    @Test public void testGetItemsFromCloud() throws Exception {
        BDDMockito.given(mDataCache.isItemListCached()).willReturn(false);
        BDDMockito.given(mDataCache.isExpired()).willReturn(false);
        mDataStoreFactory.getItems();
        Mockito.verify(mDataCache,Mockito.never()).getItemList();
    }


}