package com.flux.img

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
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
import coil3.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flux.img.R
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
object CoilLoader {

    /**
     * 加载网络图片
     * @param imageView
     * @param data url 或者本地路径
     * */
    @JvmStatic
    fun load(imageView: ImageView, data: Any?) {
        imageView.load(data) {
            placeholder(R.drawable.coil_placeholder_bg)
            error(R.drawable.coil_placeholder_bg)
        }
    }

    /**
     * 加载网络图片
     * @param imageView
     * @param data url 或者本地路径
     * */
    @JvmStatic
    fun loadBlack(imageView: ImageView, data: Any?) {
        imageView.load(data) {
            placeholder(R.drawable.coil_placeholder_bg_black)
            error(R.drawable.coil_placeholder_bg_black)
        }
    }

    /**
     * 加载网络图片
     * @param imageView
     * @param data url 或者本地路径
     * @param placeholder 占位图id
     * */
    @JvmStatic
    fun load(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int) {
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
     * @param data url 或者本地路径
     * */
    @JvmStatic
    fun loadCircle(imageView: ImageView, data: Any?) {
        imageView.load(data) {
            placeholder(R.drawable.coil_circle_placeholder_bg)
            error(R.drawable.coil_circle_placeholder_bg)
            transformations(CircleCropTransformation())
        }
    }

    /**
     * 加载圆形图片
     * @param imageView
     * @param data url 或者本地路径
     * */
    @JvmStatic
    fun loadCircle(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int) {
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
     * @param data url 或者本地路径
     * @param radius 圆角半径 px
     * */
    @JvmStatic
    fun loadRounded(
        imageView: ImageView,
        data: Any?,
        @Px radius: Float,
    ) {
        imageView.load(data) {
            placeholder(R.drawable.coil_rounded_placeholder_bg)
            error(R.drawable.coil_rounded_placeholder_bg)
            transformations(RoundedCornersTransformation(radius))
        }
    }

    /**
     * 加载圆角图片
     * @param imageView
     * @param data url 或者本地路径
     * @param radius 圆角半径 px
     * */
    @JvmStatic
    fun loadRounded(
        imageView: ImageView,
        data: Any?,
        @Px radius: Float,
        @DrawableRes placeholder: Int
    ) {
        val placeholderId =
            if (placeholder == 0) R.drawable.coil_rounded_placeholder_bg else placeholder
        imageView.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
            transformations(RoundedCornersTransformation(radius))
        }
    }

    @JvmStatic
    fun loadRounded(
        imageView: ImageView,
        data: Any?,
        @Px radius: Float,
        cornersType: RoundedCornersType = RoundedCornersType.ALL
    ) {
        imageView.load(data) {
            placeholder(R.drawable.coil_rounded_placeholder_bg)
            error(R.drawable.coil_rounded_placeholder_bg)
            transformations(createRoundedCornersTransformation(cornersType, radius))
        }
    }


    /**
     * 加载圆角图片
     * @param imageView
     * @param data url 或者本地路径
     * @param radius 圆角半径 px
     * */
    @JvmStatic
    fun loadRounded(
        imageView: ImageView,
        data: Any?,
        @Px radius: Float,
        cornersType: RoundedCornersType = RoundedCornersType.ALL,
        @DrawableRes placeholder: Int
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
     * 加载网络图片
     * @param imageView
     * @param data url 或者本地路径
     * @param radius 高斯模糊半径(注意不是像素)
     * */
    @JvmStatic
    fun loadBlur(imageView: ImageView, data: Any?, radius: Float) {
        imageView.load(data) {
            placeholder(R.drawable.coil_placeholder_bg)
            error(R.drawable.coil_placeholder_bg)
            transformations(
                BlurTransformation(
                    imageView.context,
                    radius.requireBlurRadius()
                )
            )
        }
    }

    /**
     * 加载网络图片
     * @param imageView
     * @param data url 或者本地路径
     * @param radius 高斯模糊半径(注意不是像素)
     * @param placeholder 占位图
     * */
    @JvmStatic
    fun loadBlur(
        imageView: ImageView,
        data: Any?,
        radius: Float,
        @DrawableRes placeholder: Int
    ) {
        val placeholderId =
            if (placeholder == 0) R.drawable.coil_placeholder_bg else placeholder
        imageView.load(data) {
            placeholder(placeholderId)
            error(placeholderId)
            transformations(BlurTransformation(imageView.context, radius.requireBlurRadius()))
        }
    }

    /**
     * 加载webp动图
     * @param imageView
     * @param data
     * @param placeholder
     * */
    @JvmStatic
    fun loadWebpAnim(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int) {
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
     * @param data
     * @param placeholder
     * */
    @JvmStatic
    fun loadGif(imageView: ImageView, data: Any?, @DrawableRes placeholder: Int) {
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
     * @param url  或者本地路径
     * @param listener 获取 bitmap
     * */
    @JvmStatic
    fun <T> loadBitmap(imageView: ImageView, url: String?, listener: LoadListener<T>) {
        val request = ImageRequest.Builder(imageView.context)
            .data(url)
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
     * @param url url 或者本地路径
     * @param listener 获取 Drawable
     * */
    @JvmStatic
    fun <T> loadDrawable(imageView: ImageView, url: String?, listener: LoadListener<T>) {
        val request = ImageRequest.Builder(imageView.context)
            .data(url)
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
     * 预加载网络图片到磁盘缓存中
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
     * */
    @JvmStatic
    fun preDownload(url: String?, context: Context) {
        val request = ImageRequest.Builder(context)
            .data(url)
            //禁止内存缓存
            .memoryCachePolicy(CachePolicy.DISABLED)
            //跳过解码
            .decoderFactory(BlackholeDecoder.Factory())
            .build()
        context.imageLoader.enqueue(request)
    }

    /**
     * 预下载图片并监听下载完成
     * @param url 图片的 URL
     * @param context 上下文
     * @param listener 下载监听器
     */
    @JvmStatic
    fun preDownloadWithListener(url: String?, context: Context, listener: LoadListener<Unit>) {
        val request = ImageRequest.Builder(context)
            .data(url) // 设置图片 URL
            .memoryCachePolicy(CachePolicy.DISABLED) // 禁止内存缓存，确保下载到磁盘
            .listener(
                onStart = {
                    listener.onStart() // 下载开始
                },
                onCancel = {
                    listener.onCancel() // 下载取消
                },
                onError = { _, result ->
                    listener.onError(result.throwable) // 下载失败
                },
                onSuccess = { _, _ ->
                    // 下载成功，图片已缓存到磁盘
                    listener.onSuccess(Unit) // 下载成功
                }
            )
            .build()
        context.imageLoader.enqueue(request) // 执行请求
    }


    private fun createRoundedCornersTransformation(
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
            RoundedCornersType.RIGHT_DIAGONAL -> RoundedCornersTransformation(
                0f,
                radius,
                radius,
                0F
            )

            RoundedCornersType.TOP -> RoundedCornersTransformation(radius, radius, 0f, 0f)
            RoundedCornersType.BOTTOM -> RoundedCornersTransformation(0f, 0f, radius, radius)
            RoundedCornersType.LEFT -> RoundedCornersTransformation(radius, 0f, radius, 0f)
            RoundedCornersType.RIGHT -> RoundedCornersTransformation(0f, radius, 0f, radius)
        }
        return cornersTransformation
    }

}