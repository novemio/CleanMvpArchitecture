package com.xix.cleanMvpArchitecture.presentation.screens.itemDetails;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xix.cleanMvpArchitecture.R;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.presentation.base.mvp.BaseActivity;

import static com.xix.cleanMvpArchitecture.R.id.toolbar;

/**
 * The type Item details activity.
 */
public class ItemDetailsActivity extends BaseActivity {

    private static final String ITEM_EXTRA = "item";
    private Item mItem;

    @BindView(R.id.tv_description) TextView mDescription;
    @BindView(R.id.tv_title) TextView mTitle;
    @BindView(toolbar) Toolbar mToolbar;
    @BindView(R.id.iv_itemImage) ImageView mItemImage;
    @BindView(R.id.appbar) AppBarLayout mAppBarLayout;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    @Override protected void setUp() {
        initActionBar();
        getExtra();
        initView();
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        }
    }

    /**
     * Initialize view
     */
    private void initView() {
        mTitle.setText(mItem.getTitle());
        mDescription.setText(mItem.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(mItem.getDominantColor());
        }
        Glide.with(this).load(mItem.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mItemImage);
    }

    private void getExtra() {
        mItem = getIntent().getExtras().getParcelable(ITEM_EXTRA);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Intent intent builder.
     *
     * @param context the context
     * @return the intent builder
     */
    public static IntentBuilder intent(Context context) {
        return new IntentBuilder(context);
    }

    /**
     * The type Intent builder.
     */
    public static class IntentBuilder extends com.xix.cleanMvpArchitecture.presentation.base.IntentBuilder<ItemDetailsActivity> {

        /**
         * Instantiates a new Intent builder for ItemDetails Activity.
         *
         * @param context the context
         */
        IntentBuilder(Context context) {
            super(context, ItemDetailsActivity.class);
        }

        /**
         * Put item parcelable to extra .
         *
         * @param item the item
         * @return the intent builder
         */
        public IntentBuilder item(Item item) {
            putParcelable(ItemDetailsActivity.ITEM_EXTRA, item);
            return this;
        }
    }
}
