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
import coil3.request.Disposable
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.size.ViewSizeResolver
import coil3.toBitmap
import coil3.transform.CircleCropTransformation
import coil3.transform.RoundedCornersTransformation
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
 *     @since   : 2025/2/17
 * </pre>
 */


/**
 * 加载网络图片
 * @param data 网络图片url / 本地res资源 / 本地文件file
 * @param placeholder 加载占位图 & 错误占位图
 * */

fun ImageView.load(
    data: Any?,
    @DrawableRes placeholder: Int = 0
): Disposable {
    val placeholderId = if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
    return this.load(data) {
        placeholder(placeholderId)
        error(placeholderId)
    }
}

/**
 * 加载圆形图片
 * @param data 网络图片url / 本地res资源 / 本地文件file
 * @param placeholder 加载占位图 & 错误占位图
 * */

fun ImageView.loadCircle(data: Any?, @DrawableRes placeholder: Int = 0): Disposable {
    val placeholderId = if (placeholder == 0) R.drawable.coil_circle_placeholder_bg else placeholder
    return this.load(data) {
        placeholder(placeholderId)
        error(placeholderId)
        transformations(CircleCropTransformation())
    }
}

/**
 * 加载圆角图片
 * @param data 网络图片url / 本地res资源 / 本地文件file
 * @param radius 圆角半径 px
 * @param placeholder 加载占位图 & 错误占位图
 * @param cornersType RoundedCornersType  圆角类型
 * */

fun ImageView.loadRounded(
    data: Any?,
    @Px radius: Float,
    cornersType: RoundedCornersType = RoundedCornersType.ALL,
    @DrawableRes placeholder: Int = 0
): Disposable {
    val placeholderId =
        if (placeholder == 0) R.drawable.coil_rounded_placeholder_bg else placeholder
    return this.load(data) {
        placeholder(placeholderId)
        error(placeholderId)
        transformations(createRoundedCornersTransformation(cornersType, radius))
    }
}


/**
 * 加载高斯模糊
 * @param data 网络图片url / 本地res资源 / 本地文件file
 * @param blurRadius 高斯模糊半径(0-25之间,注意不是像素)
 * @param radius 圆角半径 -1圆形
 * @param cornersType RoundedCornersType  圆角类型
 *@param placeholder 加载占位图 & 错误占位图
 * */

fun ImageView.loadBlur(
    data: Any?,
    blurRadius: Float = 10f,
    radius: Float = 0f,
    cornersType: RoundedCornersType = RoundedCornersType.ALL,
    @DrawableRes placeholder: Int = 0
): Disposable {
    val placeholderId = if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
    val transformations = mutableListOf<coil3.transform.Transformation>()
    transformations.add(BlurTransformation(context, blurRadius.requireBlurRadius()))
    if (radius > 0) {
        transformations.add(createRoundedCornersTransformation(cornersType, radius))
    } else if (radius < 0) {
        transformations.add(CircleCropTransformation())
    }
    return this.load(data) {
        placeholder(placeholderId)
        error(placeholderId)
        transformations(transformations)
    }
}


/**
 * 加载webp动图
 * @param data 网络图片url / 本地res资源 / 本地文件file
 * @param placeholder 加载占位图 & 错误占位图
 * */
fun ImageView.loadWebpAnim(data: Any?, @DrawableRes placeholder: Int = 0): Disposable? {
    val placeholderId = if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
    //Android 9.0 使用coil 解析
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        return this.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
        }
    }
    // Android 9.0 以下 使用glide + webpdecoder 来实现
    val circleCrop: Transformation<Bitmap> = CircleCrop()
    Glide.with(this)
        .load(data)
        .optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(circleCrop))
        .into(this)
    return null
}

/**
 * 加载Gif动图
 * @param data 网络图片url / 本地res资源 / 本地文件file
 * @param placeholder 加载占位图 & 错误占位图
 * */
fun ImageView.loadGif(data: Any?, @DrawableRes placeholder: Int = 0): Disposable {
    val placeholderId = if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
    return this.load(data) {
        placeholder(placeholderId)
        error(placeholderId)
    }
}

/**
 * 加载网络图片 & 获取Bitmap
 * @param url url
 * @param listener 加载监听
 * */
fun <T> ImageView.loadBitmap(url: String?, listener: LoadListener<T>) {
    val request = ImageRequest.Builder(context)
        .data(url)
        .size(ViewSizeResolver(this))
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
    context.imageLoader.enqueue(request)
}

/**
 * 加载网络图片 & 获取Drawable
 * @param url url
 * @param listener 加载监听
 * */
fun <T> ImageView.loadDrawable(url: String?, listener: LoadListener<T>) {
    val request = ImageRequest.Builder(context)
        .data(url)
        .size(ViewSizeResolver(this))
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
                listener.onSuccess(result.image.asDrawable(resources) as T)
            }
        )
        .build()
    context.imageLoader.enqueue(request)
}


/**
 * 预加载网络图片
 * @param url
 * */
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

fun createRoundedCornersTransformation(
    cornersType: RoundedCornersType,
    radius: Float
): RoundedCornersTransformation {
    val cornersTransformation = when (cornersType) {
        RoundedCornersType.ALL -> RoundedCornersTransformation(radius)
        RoundedCornersType.TOP_LEFT -> RoundedCornersTransformation(radius, 0f, 0f, 0f)
        RoundedCornersType.TOP_RIGHT -> RoundedCornersTransformation(0f, radius, 0f, 0f)
        RoundedCornersType.BOTTOM_LEFT -> RoundedCornersTransformation(0f, 0f, radius, 0f)
        RoundedCornersType.BOTTOM_RIGHT -> RoundedCornersTransformation(0f, 0f, 0f, radius)
        RoundedCornersType.LEFT_DIAGONAL -> RoundedCornersTransformation(radius, 0f, 0f, radius)
        RoundedCornersType.RIGHT_DIAGONAL -> RoundedCornersTransformation(0f, radius, radius, 0F)
        RoundedCornersType.TOP -> RoundedCornersTransformation(radius, radius, 0f, 0f)
        RoundedCornersType.BOTTOM -> RoundedCornersTransformation(0f, 0f, radius, radius)
        RoundedCornersType.LEFT -> RoundedCornersTransformation(radius, 0f, radius, 0f)
        RoundedCornersType.RIGHT -> RoundedCornersTransformation(0f, radius, 0f, radius)

    }
    return cornersTransformation
}
