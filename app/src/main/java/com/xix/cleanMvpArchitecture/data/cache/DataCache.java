package com.xix.cleanMvpArchitecture.data.cache;

import com.xix.cleanMvpArchitecture.data.model.Item;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by xix on 4/8/17.
 */

public interface DataCache {

    Observable<List<Item>> getItemList();
    void putItems(List<Item> itemList);

    boolean isExpired();
    boolean isCached(int id);

    boolean isItemListCached();


    void  clearAll();
}
