package com.xix.cleanMvpArchitecture.data;

import android.content.Context;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.data.store.DataStoreFactory;
import com.xix.cleanMvpArchitecture.di.ApplicationContext;
import com.xix.cleanMvpArchitecture.domain.DataManager;
import com.xix.cleanMvpArchitecture.domain.PostExecutionThread;
import com.xix.cleanMvpArchitecture.domain.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xix on 3/12/17.
 */

@Singleton
public class DataManagerImpl implements DataManager {
    private Context mContext;
    private DataStoreFactory mMDataStoreFactory;
    private PostExecutionThread mPostExecutionThread;

    @Inject public DataManagerImpl(@ApplicationContext Context context, DataStoreFactory mDataStoreFactory, PostExecutionThread postExecutionThread) {
        mContext = context;
        mMDataStoreFactory = mDataStoreFactory;
        mPostExecutionThread = postExecutionThread;
    }

   public UseCase<List<Item>> getItems() {
        Observable<List<Item>> listObservable =
            mMDataStoreFactory.getItems().subscribeOn(Schedulers.io()).observeOn(mPostExecutionThread.getScheduler());
        return new UseCase<>(listObservable);
    }
}
