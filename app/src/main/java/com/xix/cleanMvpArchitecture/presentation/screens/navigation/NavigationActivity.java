package com.xix.cleanMvpArchitecture.presentation.screens.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xix.cleanMvpArchitecture.R;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.presentation.base.mvp.BaseActivity;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpPresenter;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpView;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by xix on 4/8/17.
 */

public class NavigationActivity extends BaseActivity implements NavigationMvpView {
    private static final String LOG_TAG = NavigationActivity.class.getSimpleName();
    @Inject NavigationMvpPresenter<NavigationMvpView> mPresenter;
    @Inject ItemAdapter mItemAdapter;
    @BindView(R.id.rv_itemList) RecyclerView mItemList;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
        setUp();
    }

    @Override public void setItemList(List<Item> itemList) {
        Log.d(LOG_TAG, "setItemList: " + itemList);
        mItemAdapter.setCollection(itemList);
        Log.d(LOG_TAG, "setItemList: "+mItemAdapter.getItemCount());
    }

    @Override protected void setUp() {
        mPresenter.onStart();
        setupListView();
    }

    private void setupListView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        mItemList.setLayoutManager(layoutManager);
        mItemList.setAdapter(mItemAdapter);
    }
}
