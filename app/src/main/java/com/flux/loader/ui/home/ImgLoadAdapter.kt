package com.flux.loader.ui.home

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.flux.img.R
import com.flux.img.loadCircle
import com.flux.loader.ImgData
import com.flux.loader.netImg

/**
 * <pre>
 *     类描述  :
 *
 *     @author : never
 *     @since  : 2025/12/5
 * </pre>
 */
class ImgLoadAdapter : BaseQuickAdapter<ImgData, QuickViewHolder>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        // 返回一个 ViewHolder
        return QuickViewHolder(com.flux.loader.R.layout.layout_img_item, parent)
    }

    override fun onBindViewHolder(
        holder: QuickViewHolder,
        position: Int,
        item: ImgData?
    ) {
        val ivImg = holder.getView<ImageView>(com.flux.loader.R.id.iv_img)
        item?.let { data ->
            when (data.type) {
                0 -> {
                    ivImg.loadCircle(netImg)
                }
            }
        }
    }
}