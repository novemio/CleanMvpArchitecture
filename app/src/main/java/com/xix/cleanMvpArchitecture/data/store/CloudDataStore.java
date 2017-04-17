package com.xix.cleanMvpArchitecture.data.store;

import com.xix.cleanMvpArchitecture.data.cache.DataCache;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.data.network.RestClient;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by xix on 4/8/17.
 */

public class CloudDataStore implements DataStore {
    private static final String LOG_TAG = CloudDataStore.class.getSimpleName();
    private RestClient mRestClient;
    private DataCache mDataCache;

    public CloudDataStore(DataCache dataCache,RestClient restClient) {
        mDataCache = dataCache;
        mRestClient = restClient;
    }

    @Override public Observable<List<Item>> getItems() {
        return mRestClient.getItems().doOnNext( itemList -> mDataCache.putItems(itemList));
    }
}
