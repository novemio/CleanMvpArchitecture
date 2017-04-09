package com.xix.cleanMvpArchitecture.data.store;

import android.content.Context;
import com.xix.cleanMvpArchitecture.data.cache.DataCache;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.di.ApplicationContext;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;




/**
 * Factory that creates different implementations of {@link DataStore}.
 */
@Singleton
public class DataStoreFactory implements DataStore {

  private final Context context;
  private final DataCache mDataCache;
  private final DataStore mCloudDataStore;
  private final DataStore mDiskDataStore;

  @Inject
  public DataStoreFactory(@ApplicationContext Context  context, DataCache dataCache) {
    if (context == null || dataCache == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.mDataCache = dataCache;
     mCloudDataStore = createCloudDataStore();
     mDiskDataStore = createDiskDataStore();
  }

  private DiskDataStore createDiskDataStore() {
    return new DiskDataStore(mDataCache);
  }

  @Override public Observable<List<Item>> getItems() {

    if(!mDataCache.isExpired()){
      return mDiskDataStore.getItems();
    }

    return mCloudDataStore.getItems();


  }

  /**
   * Create {@link DataStoreFactory} to retrieve data from the Cloud.
   */
  private DataStore createCloudDataStore() {
    return new CloudDataStore(mDataCache);
  }
}
