package com.xix.cleanMvpArchitecture.domain;

import com.xix.cleanMvpArchitecture.data.model.Item;
import java.util.List;

/**
 * Created by xix on 4/8/17.
 */

public interface DataManager {
    UseCase<List<Item>> getItems();
}
