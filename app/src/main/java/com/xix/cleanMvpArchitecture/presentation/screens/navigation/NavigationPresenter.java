package com.xix.cleanMvpArchitecture.presentation.screens.navigation;

import android.util.Log;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.domain.DataManager;
import com.xix.cleanMvpArchitecture.domain.DefaultObserver;
import com.xix.cleanMvpArchitecture.domain.UseCase;
import com.xix.cleanMvpArchitecture.presentation.base.mvp.BasePresenter;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpPresenter;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpView;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by xix on 4/8/17.
 */

public class NavigationPresenter<V extends NavigationMvpView> extends BasePresenter<V> implements NavigationMvpPresenter<V> {
    private static final String LOG_TAG = NavigationPresenter.class.getSimpleName();

    @Inject public NavigationPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override public void onStart() {
        Log.d(LOG_TAG, "onStart: ");
        UseCase<List<Item>> items = getDataManager().getItems();
        getCompositeDisposable().add(items.execute(new DefaultObserver<List<Item>>() {
            @Override public void onNext(List<Item> items) {
                getMvpView().setItemList(items);
            }
        }));
    }
}
