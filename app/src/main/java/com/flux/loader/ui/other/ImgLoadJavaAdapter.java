package com.flux.loader.ui.other;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.chad.library.adapter4.viewholder.QuickViewHolder;
import com.flux.img.FluxLoader;
import com.flux.loader.ExtKt;
import com.flux.loader.ImgData;

import java.io.File;

/**
 * <pre>
 *     类描述  :
 *
 *     @author : never
 *     @since  : 2025/12/8
 * </pre>
 */
public class ImgLoadJavaAdapter extends BaseQuickAdapter<ImgData, QuickViewHolder> {
    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(com.flux.loader.R.layout.layout_img_item, viewGroup);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder holder, int i, @Nullable ImgData imgData) {
        ImageView ivImg = holder.getView(com.flux.loader.R.id.iv_img);
        switch (imgData.getType()) {
            case 0:
                FluxLoader.load(ivImg, ExtKt.getNetImg());
                break;
            case 1:
                FluxLoader.loadRounded(ivImg, ExtKt.getNetImg(), ExtKt.getDp(12));
                break;
            case 2:
                FluxLoader.loadCircle(ivImg, ExtKt.getNetImg());
                break;
            case 3:
                FluxLoader.loadBlur(ivImg, ExtKt.getNetImg(), 10);
                break;
            case 4:
                FluxLoader.loadBlur(ivImg, ExtKt.getNetImg(), 10, ExtKt.getDp(12));
                break;
            case 5:
                FluxLoader.loadBlur(ivImg, ExtKt.getNetImg(), 10, -1);
                break;
            case 6:
                FluxLoader.loadWebpAnim(ivImg, ExtKt.getNetWebp(), 0);
                break;
            case 7:
                FluxLoader.loadGif(ivImg, ExtKt.getNetGif(), 0);
                break;
            case 8:
                File video = ExtKt.copyAssetToCache(getContext(), "video.mp4");
                FluxLoader.load(ivImg, video);
                break;
        }
    }
}
