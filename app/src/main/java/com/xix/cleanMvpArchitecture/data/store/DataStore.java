package com.xix.cleanMvpArchitecture.data.store;

import com.xix.cleanMvpArchitecture.data.model.Item;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by xix on 4/8/17.
 */

interface DataStore {

    Observable<List<Item>>getItems();
}
