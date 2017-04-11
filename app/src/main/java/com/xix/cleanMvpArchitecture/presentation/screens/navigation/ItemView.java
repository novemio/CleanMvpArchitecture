package com.xix.cleanMvpArchitecture.presentation.screens.navigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xix.cleanMvpArchitecture.R;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.presentation.base.adapter.AdvancedView;
import com.xix.cleanMvpArchitecture.presentation.base.adapter.HasViews;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xix on 4/9/17.
 */

class ItemView extends AdvancedView<Item> {
    private static final String LOG_TAG = ItemView.class.getSimpleName();
    private TextView mTitle;
    private TextView mDescription;
    private CircleImageView mImageView;

    public ItemView(Context context) {
        super(context);
    }

    @Override protected void onViewChanged(HasViews view) {
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mDescription = (TextView) view.findViewById(R.id.tv_description);
        mImageView = (CircleImageView) view.findViewById(R.id.iv_itemImage);


    }

    @Override protected int getLayoutId() {
        return R.layout.row_item_list;
    }

    @Override public void bind(Item item) {
        Log.d(LOG_TAG, "bind: " + item);
        mTitle.setText(item.getTitle());
        mDescription.setText(item.getDescription());


        Glide.with(getContext())
            .load(item.getImageUrl())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                    boolean isFromMemoryCache, boolean isFirstResource) {
                    Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                    Palette.from(bitmap).generate(palette -> {
                      item.setDominantColor(palette.getDominantColor(getResources().getColor(R.color.colorPrimary)));
                        mImageView.setBorderColor(item.getDominantColor());
                    });
                    return false;
                }
            })
            .into(mImageView);

    }

    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }
}
