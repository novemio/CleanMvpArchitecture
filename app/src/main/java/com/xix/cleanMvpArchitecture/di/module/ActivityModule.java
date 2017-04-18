package com.xix.cleanMvpArchitecture.di.module;

import android.app.Activity;
import android.content.Context;
import com.xix.cleanMvpArchitecture.di.ActivityContext;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.NavigationPresenter;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpPresenter;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpView;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xix on 3/12/17.
 */

@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides @ActivityContext Context provideContext() {
        return mActivity;
    }

    @Provides Activity provideActivity() {
        return mActivity;
    }

    @Provides CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides NavigationMvpPresenter<NavigationMvpView> provideNavigationPresenter(NavigationPresenter<NavigationMvpView> navigationPresenter) {
        return navigationPresenter;
    }
}
