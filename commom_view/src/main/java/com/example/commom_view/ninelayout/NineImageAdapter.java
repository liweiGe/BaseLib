package com.example.commom_view.ninelayout;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class NineImageAdapter implements NineGridView.NineGridAdapter<ImageData> {

    private List<ImageData> mImageBeans;
    public Context mContext;

    public NineImageAdapter(Context context, List<ImageData> imageBeans) {
        this.mImageBeans = imageBeans;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    @Override
    public ImageData getItem(int position) {
        return mImageBeans == null ? null :
                position < mImageBeans.size() ? mImageBeans.get(position) : null;
    }

    @Override
    public View getView(int position, View itemView) {
        ImageView imageView;
        if (itemView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) itemView;
        }
        // TODO: 2019-09-16 这里的imageview需要加载图片
//        imageView.setData(mImageBeans.get(position));
        return imageView;
    }
}
