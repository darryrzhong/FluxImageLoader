package com.flux.loader.ui.home

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.flux.img.R
import com.flux.img.load
import com.flux.img.loadBlur
import com.flux.img.loadCircle
import com.flux.img.loadGif
import com.flux.img.loadRounded
import com.flux.img.loadWebpAnim
import com.flux.img.transform.RoundedCornersType
import com.flux.loader.ImgData
import com.flux.loader.copyAssetToCache
import com.flux.loader.dp
import com.flux.loader.netGif
import com.flux.loader.netImg
import com.flux.loader.netWebp
import java.io.File
import java.io.FileOutputStream

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
                    ivImg.load(netImg)
                }

                1 -> {
                    ivImg.loadRounded(
                        netImg,
                        12f.dp.toFloat()
                    )
                }

                2 -> {
                    ivImg.loadCircle(netImg)
                }

                3 -> {
                    ivImg.loadBlur(netImg, 10f)
                }

                4 -> {
                    ivImg.loadBlur(netImg, 10f, 12f.dp.toFloat())
                }

                5 -> {
                    ivImg.loadBlur(netImg, 10f, -1f)
                }

                6 -> {
                    ivImg.loadWebpAnim(netWebp)
                }

                7 -> {
                    ivImg.loadGif(netGif)
                }

                8 -> {
                    val file = copyAssetToCache(context, "video.mp4")
                        ivImg.load(file)
                }

            }
        }
    }


}