package com.flux.loader

import android.app.Application
import android.content.ComponentCallbacks2
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

    override fun onLowMemory() {
        super.onLowMemory()
        // 系统内存紧张，立即清理内存缓存
        FluxImageLoader.getInstance().clearMemoryCache(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        when (level) {
            //App 切到后台，UI 不可见 || 后台 App 内存低
            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN, ComponentCallbacks2.TRIM_MEMORY_BACKGROUND -> {
                FluxImageLoader.getInstance().trimMemoryCache(this)

            }

            else -> {}
        }
    }
}