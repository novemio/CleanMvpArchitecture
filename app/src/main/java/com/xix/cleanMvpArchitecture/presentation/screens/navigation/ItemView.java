package com.xix.cleanMvpArchitecture.presentation.screens.navigation;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import com.xix.cleanMvpArchitecture.R;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.presentation.base.adapter.AdvancedView;
import com.xix.cleanMvpArchitecture.presentation.base.adapter.HasViews;

/**
 * Created by xix on 4/9/17.
 */

class ItemView extends AdvancedView<Item> {
    private static final String LOG_TAG = ItemView.class.getSimpleName();
    private TextView mTitle;

    public ItemView(Context context) {
        super(context);
    }

    @Override protected void onViewChanged(HasViews view) {
        mTitle = (TextView) view.findViewById(R.id.tv_title);
    }

    @Override protected int getLayoutId() {
        return R.layout.row_item_list;
    }

    @Override public void bind(Item item) {
        Log.d(LOG_TAG, "bind: "+item);
        mTitle.setText(item.getTitle());
    }
}
