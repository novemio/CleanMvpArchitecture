package com.xix.cleanMvpArchitecture.presentation.screens.navigation;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xix.cleanMvpArchitecture.R;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.presentation.base.IntentBuilder;
import com.xix.cleanMvpArchitecture.presentation.base.adapter.RecyclerViewAdapterBase;
import com.xix.cleanMvpArchitecture.presentation.base.mvp.BaseActivity;
import com.xix.cleanMvpArchitecture.presentation.screens.itemDetails.ItemDetailsActivity;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpPresenter;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.contract.NavigationMvpView;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by xix on 4/8/17.
 */

public class NavigationActivity extends BaseActivity implements NavigationMvpView, RecyclerViewAdapterBase.OnItemClickListener<Item> {
    private static final String LOG_TAG = NavigationActivity.class.getSimpleName();
    @Inject NavigationMvpPresenter<NavigationMvpView> mPresenter;
    @Inject ItemAdapter mItemAdapter;
    @BindView(R.id.rv_itemList) RecyclerView mItemList;
    @BindView(R.id.appBarLayout) AppBarLayout mAppBarLayout;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);
        //setupWindowAnimations();
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
        setUp();
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(5000);
            getWindow().setEnterTransition(fade);
        }
    }

    @Override public void setItemList(List<Item> itemList) {
        Log.d(LOG_TAG, "setItemList: " + itemList);
        mItemAdapter.setCollection(itemList);
        Log.d(LOG_TAG, "setItemList: " + mItemAdapter.getItemCount());
    }

    @Override protected void setUp() {
        mPresenter.getItems();
        setupListView();
    }


    private void setupListView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mItemList.setLayoutManager(layoutManager);
        mItemList.setAdapter(mItemAdapter);
        mItemAdapter.setOnItemClickListener(this);
        mItemList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisiblePosition =
                        ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    if (firstVisiblePosition == 0) {
                        mAppBarLayout.setExpanded(true, true);
                    }
                }
            }
        });
    }

    @Override public void onItemClick(Item item) {
        ItemDetailsActivity.intent(this).item(item).start();
    }

    public static IntentBuilder intent(Context context) {
        return new IntentBuilder<>(context, NavigationActivity.class);
    }
}
