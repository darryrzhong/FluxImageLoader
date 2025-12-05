package com.flux.loader

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * <pre>
 *     类描述  :
 *
 *     @author : never
 *     @since  : 2025/12/5
 * </pre>
 */
class SquareFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 先按原逻辑测量父级建议大小
        setMeasuredDimension(
            getDefaultSize(0, widthMeasureSpec),
            getDefaultSize(0, heightMeasureSpec)
        )

        // 使用最终宽度作为“正方形尺寸”
        val size = measuredWidth
        val squareMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)

        super.onMeasure(squareMeasureSpec, squareMeasureSpec)
    }
}