package com.xix.cleanMvpArchitecture.data;

import android.test.mock.MockContext;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.data.store.DataStoreFactory;
import com.xix.cleanMvpArchitecture.domain.DataManager;
import com.xix.cleanMvpArchitecture.domain.DefaultObserver;
import com.xix.cleanMvpArchitecture.domain.PostExecutionThread;
import com.xix.cleanMvpArchitecture.domain.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by xix on 4/18/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DataManagerImplTest {
    private static final int MAX_ITEMS = 3;
    private static final int DELAY_SEC = 2;
    private DataManager mDataManager;
    @Mock DataStoreFactory mockDataStoreFactory;
    @Mock PostExecutionThread mockPostExecutionThread;
    private MockContext mockContext;
    private CountDownLatch lock = new CountDownLatch(1);

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDataManager = new DataManagerImpl(mockContext, mockDataStoreFactory, mockPostExecutionThread);
        given(mockPostExecutionThread.getScheduler()).willReturn(Schedulers.newThread());
        given(mockDataStoreFactory.getItems()).willReturn(Observable.just(createFakeItemList()));
    }

    @Test public void testGetItems() throws Exception {

        final boolean[] status = { false };

        UseCase<List<Item>> items = mDataManager.getItems();
        verify(mockDataStoreFactory).getItems();

        items.execute(new DefaultObserver<List<Item>>() {
            @Override public void onNext(List<Item> itemList) {
                assertEquals(itemList.size(), MAX_ITEMS);
                status[0] = true;
                lock.countDown();
            }
        });
        lock.await(DELAY_SEC, TimeUnit.SECONDS);
        assertEquals(status[0], true);
    }

    private List<Item> createFakeItemList() {
        Item fakeItem = new Item();
        ArrayList<Item> fakeItemList = new ArrayList<>();
        for (int i=0;i< MAX_ITEMS;i++){
            fakeItemList.add(fakeItem);
        }

        return fakeItemList;
    }
}