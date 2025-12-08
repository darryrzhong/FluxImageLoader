package com.flux.img

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import coil3.annotation.ExperimentalCoilApi
import coil3.asDrawable
import coil3.decode.BlackholeDecoder
import coil3.imageLoader
import coil3.load
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.size.ViewSizeResolver
import coil3.toBitmap
import coil3.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flux.img.listener.LoadListener
import com.flux.img.transform.BlurTransformation
import com.flux.img.transform.RoundedCornersType
import com.flux.img.transform.requireBlurRadius


/**
 * <pre>
 *     类描述  :
 *
 *
 *     @author : never
 *     @since   : 2025/1/9
 * </pre>
 */
object FluxLoader {

    /**
     * 加载图片
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param placeholder  加载占位图 & 错误占位图
     * */
    @JvmStatic
    @JvmOverloads
    fun load(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int = 0) {
        val placeholderId =
            if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
        imageView.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
        }
    }

    /**
     * 加载圆形图片
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param placeholder  加载占位图 & 错误占位图
     * */
    @JvmStatic
    @JvmOverloads
    fun loadCircle(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int = 0) {
        val placeholderId =
            if (placeholder == 0) R.drawable.coil_circle_placeholder_bg else placeholder
        imageView.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
            transformations(CircleCropTransformation())
        }
    }


    /**
     * 加载圆角图片
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param radius 圆角半径 px
     * @param placeholder  加载占位图 & 错误占位图
     * @param cornersType RoundedCornersType  圆角类型
     * */
    @JvmStatic
    @JvmOverloads
    fun loadRounded(
        imageView: ImageView,
        data: Any?,
        @Px radius: Float,
        cornersType: RoundedCornersType = RoundedCornersType.ALL,
        @DrawableRes placeholder: Int = 0
    ) {
        val placeholderId =
            if (placeholder == 0) R.drawable.coil_rounded_placeholder_bg else placeholder
        imageView.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
            transformations(createRoundedCornersTransformation(cornersType, radius))
        }
    }

    /**
     * 加载高斯模糊效果图片
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param blurRadius 高斯模糊半径(0-25之间,注意不是像素)
     * @param radius 圆角半径 -1圆形
     * @param cornersType RoundedCornersType  圆角类型
     * @param placeholder  加载占位图 & 错误占位图
     * */
    @JvmStatic
    @JvmOverloads
    fun loadBlur(
        imageView: ImageView, data: Any?,
        blurRadius: Float = 10f,
        radius: Float = 0f,
        cornersType: RoundedCornersType = RoundedCornersType.ALL,
        @DrawableRes placeholder: Int = 0
    ) {
        val placeholderId = if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
        val transformations = mutableListOf<coil3.transform.Transformation>()
        transformations.add(BlurTransformation(imageView.context, blurRadius.requireBlurRadius()))
        if (radius > 0) {
            transformations.add(
                createRoundedCornersTransformation(
                    cornersType,
                    radius
                )
            )
        } else if (radius < 0) {
            transformations.add(CircleCropTransformation())
        }
        imageView.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
            transformations(transformations)
        }
    }


    /**
     * 加载webp动图
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param placeholder  加载占位图 & 错误占位图
     * */
    @JvmStatic
    @JvmOverloads
    fun loadWebpAnim(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int = 0) {
        val placeholderId = if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
        //Android 9.0 使用coil 解析
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            imageView.load(data) {
                placeholder(placeholderId)
                error(placeholderId)
            }
            return
        }
        // Android 9.0 以下 使用glide + webpdecoder 来实现
        val circleCrop: Transformation<Bitmap> = CircleCrop()
        Glide.with(imageView)
            .load(data)
            .optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(circleCrop))
            .into(imageView)
    }

    /**
     * 加载Gif动图
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param placeholder   加载占位图 & 错误占位图
     * */
    @JvmStatic
    @JvmOverloads
    fun loadGif(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int = 0) {
        val placeholderId =
            if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
        imageView.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
        }
    }

    /**
     * 加载网络图片 & 获取Bitmap or drawable
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param listener 加载监听
     * */
    @JvmStatic
    fun <T> loadBitmap(imageView: ImageView, data: Any?, listener: LoadListener<T>) {
        val request = ImageRequest.Builder(imageView.context)
            .data(data)
            .size(ViewSizeResolver(imageView))
            .listener(
                onStart = {
                    listener.onStart()
                },
                onCancel = {
                    listener.onCancel()
                },
                onError = { _, result ->
                    listener.onError(result.throwable)
                },
                onSuccess = { _, result ->
                    listener.onSuccess(result.image.toBitmap() as T)
                }
            )
            .build()
        imageView.context.imageLoader.enqueue(request)
    }

    /**
     * 加载网络图片 & 获取Drawable
     * @param imageView
     * @param data 网络图片url / 本地res资源 / 本地文件file
     * @param listener 加载监听
     * */
    @JvmStatic
    fun <T> loadDrawable(imageView: ImageView, data: Any?, listener: LoadListener<T>) {
        val request = ImageRequest.Builder(imageView.context)
            .data(data)
            .size(ViewSizeResolver(imageView))
            .listener(
                onStart = {
                    listener.onStart()
                },
                onCancel = {
                    listener.onCancel()
                },
                onError = { _, result ->
                    listener.onError(result.throwable)
                },
                onSuccess = { _, result ->
                    listener.onSuccess(result.image.asDrawable(imageView.resources) as T)
                }
            )
            .build()
        imageView.context.imageLoader.enqueue(request)
    }


    /**
     * 预加载网络图片
     * @param url
     * */
    @JvmStatic
    fun preload(url: String?, context: Context) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        context.imageLoader.enqueue(request)
    }

    /**
     * 预下载图片
     * @param url
     * @param listener 加载监听
     * */
    @OptIn(ExperimentalCoilApi::class)
    @JvmStatic
    @JvmOverloads
    fun preDownload(url: String?, context: Context, listener: LoadListener<Unit>? = null) {
        val request = ImageRequest.Builder(context)
            .data(url)
            //禁止内存缓存
            .memoryCachePolicy(CachePolicy.DISABLED)
            //跳过解码
            .decoderFactory(BlackholeDecoder.Factory())
            .listener(
                onStart = {
                    listener?.onStart() // 下载开始
                },
                onCancel = {
                    listener?.onCancel() // 下载取消
                },
                onError = { _, result ->
                    listener?.onError(result.throwable) // 下载失败
                },
                onSuccess = { _, _ ->
                    // 下载成功，图片已缓存到磁盘
                    listener?.onSuccess(Unit) // 下载成功
                }
            )
            .build()
        context.imageLoader.enqueue(request)
    }

}