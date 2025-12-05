package com.flux.img

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

/**
 * <pre>
 *     类描述  :
 *
 *
 *     @author : never
 *     @since   : 2025/2/14
 * </pre>
 */
fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable {
    return checkNotNull(AppCompatResources.getDrawable(this, resId)) { "Invalid resource ID: $resId" }
}