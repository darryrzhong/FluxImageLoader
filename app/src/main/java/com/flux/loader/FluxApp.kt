package com.flux.loader

import android.app.Application
import com.flux.img.FluxImageLoader

/**
 * <pre>
 *     类描述  :
 *
 *     @author : never
 *     @since  : 2025/11/24
 * </pre>
 */
class FluxApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FluxImageLoader.getInstance()
            .setMaxMemorySizePercent(0.1) //最大内存缓存比例
            .setMaxDiskSizePercent(0.02) //最大disk缓存比例
            .setDiskCacheFileName("flux_image_cache") //disk缓存目录文件名称
            .debug(true)
            .init(this)
    }
}