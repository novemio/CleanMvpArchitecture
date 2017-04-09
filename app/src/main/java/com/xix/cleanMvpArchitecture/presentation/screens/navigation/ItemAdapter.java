package com.xix.cleanMvpArchitecture.presentation.screens.navigation;

import android.content.Context;
import android.view.ViewGroup;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.di.ActivityContext;
import com.xix.cleanMvpArchitecture.presentation.base.adapter.RecyclerViewAdapterBase;
import com.xix.cleanMvpArchitecture.presentation.base.adapter.ViewWrapper;
import javax.inject.Inject;

/**
 * Created by xix on 4/9/17.
 */

public class ItemAdapter extends RecyclerViewAdapterBase<Item, ItemView> {

    private Context mContext;

    @Inject public ItemAdapter(@ActivityContext Context context) {
        mContext = context;
    }

    @Override protected ItemView onCreateItemView(ViewGroup parent, int viewType) {
        return new ItemView(mContext);
    }

    @Override public void onBindViewHolder(ViewWrapper<ItemView> holder, int position) {
        ItemView view = holder.getView();
        Item item = getItem(position);
        view.bind(item);
    }
}
