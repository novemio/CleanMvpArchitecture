package com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract;

import com.xix.cleanMvpArchitecture.presentation.base.mvp.MvpPresenter;

public  interface NavigationMvpPresenter<V extends NavigationMvpView> extends MvpPresenter<V> {
        
         void getItems();
    }