package com.xix.cleanMvpArchitecture.presentation.base.mvp;

/**
 * Created by xix on 27/03/17.
 */

import android.support.annotation.StringRes;

/**
 * Base interface that any class that wants to act as a NavigationMvpView in the MVP (Model NavigationMvpView NavigationMvpPresenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface MvpView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

}
