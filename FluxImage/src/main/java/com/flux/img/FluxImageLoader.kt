package com.flux.img

import android.content.Context
import android.os.Build
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.imageLoader
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import coil3.video.VideoFrameDecoder
import com.flux.img.okhttp.trustSSLCertificate
import okhttp3.OkHttpClient

/**
 * <pre>
 *     类描述  : Coil 单例管理器
 *
 *
 *     @author : never
 *     @since   : 2025/2/14
 * </pre>
 */
class FluxImageLoader private constructor() {

    companion object {
        private var instance: FluxImageLoader? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: FluxImageLoader().also { instance = it }
        }

    }

    //最大内存缓存比例 (app可用内存百分比)
    private var maxMemorySizePercent = 0.1

    //最大disk缓存比例 (用户当前手机可用磁盘大小的百分比)
    private var maxDiskSizePercent = 0.02

    //disk缓存目录文件名称
    private var diskCacheFileName = "coil_image_cache"

    //debug 日志输出模式
    private var debug: Boolean = false


    fun setMaxMemorySizePercent(maxMemorySizePercent: Double) = apply {
        this.maxMemorySizePercent = maxMemorySizePercent
    }

    fun setMaxDiskSizePercent(maxDiskSizePercent: Double) = apply {
        this.maxDiskSizePercent = maxDiskSizePercent

    }

    fun setDiskCacheFileName(diskCacheFileName: String) = apply {
        this.diskCacheFileName = diskCacheFileName
    }

    fun debug(debug: Boolean) = apply {
        this.debug = debug
    }


    fun init(context: Context) {
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(context)
                .components {
                    add(VideoFrameDecoder.Factory())
                    add(OkHttpNetworkFetcherFactory(callFactory = {
                        OkHttpClient.Builder()
                            .trustSSLCertificate()
                            .build()
                    }))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(AnimatedImageDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(context, maxMemorySizePercent)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve(diskCacheFileName))
                        .maxSizePercent(maxDiskSizePercent)
                        .build()
                }
                .crossfade(true)
//                .placeholder(context.getDrawableCompat(placeholderResId).asImage())
//                .error(context.getDrawableCompat(placeholderResId).asImage())
                .logger(if (debug) DebugLogger() else null)
                .build()

        }
    }

    fun clearMemoryCache(context: Context){
         context.imageLoader.memoryCache?.clear()
    }

}