package com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract;

import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.presentation.base.mvp.MvpView;
import java.util.List;

public  interface NavigationMvpView extends MvpView {
        
        void  setItemList(List<Item> itemList);
    }