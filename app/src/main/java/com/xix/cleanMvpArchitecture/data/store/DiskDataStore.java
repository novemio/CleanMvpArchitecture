package com.xix.cleanMvpArchitecture.data.store;

import com.xix.cleanMvpArchitecture.data.cache.DataCache;
import com.xix.cleanMvpArchitecture.data.model.Item;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by xix on 4/8/17.
 */

public class DiskDataStore implements DataStore {

    private DataCache mDataCache;

    public DiskDataStore(DataCache dataCache) {
        mDataCache = dataCache;
    }

    @Override public Observable<List<Item>> getItems() {
        return  mDataCache.getItemList();
    }
}
