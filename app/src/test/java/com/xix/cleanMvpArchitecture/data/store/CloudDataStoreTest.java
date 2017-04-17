package com.xix.cleanMvpArchitecture.data.store;

import com.xix.cleanMvpArchitecture.data.cache.DataCache;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.data.network.RestClient;
import io.reactivex.Observable;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by xix on 4/17/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CloudDataStoreTest {

    @Mock RestClient mRestClient;
    @Mock DataCache mMockDataCache;
    private CloudDataStore mCloudDataStore;

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mCloudDataStore = new CloudDataStore(mMockDataCache, mRestClient);
    }

    @Test public void testGetItemsFromRest() throws Exception {
        Item fakeItem = new Item();
        ArrayList<Item> fakeItemList = new ArrayList<>();
        fakeItemList.add(fakeItem);
        fakeItemList.add(fakeItem);


        given(mRestClient.getItems()).willReturn(Observable.just(fakeItemList));
        //willDoNothing().given(mMockDataCache).putItems(fakeItemList);

        mCloudDataStore.getItems();
        verify(mRestClient).getItems();
    }
}