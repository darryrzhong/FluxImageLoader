package com.flux.img.transform

/**
 * 用于定义 View 圆角裁剪的类型枚举
 *
 * 常用于图片控件、自定义组件，需要根据不同场景应用不同的圆角组合。
 *
 * @author     never
 * @since      2025/2/17
 */
enum class RoundedCornersType {

    /**
     * 所有四个角都为圆角
     */
    ALL,

    /**
     * 左上角圆角
     */
    TOP_LEFT,

    /**
     * 右上角圆角
     */
    TOP_RIGHT,

    /**
     * 左下角圆角
     */
    BOTTOM_LEFT,

    /**
     * 右下角圆角
     */
    BOTTOM_RIGHT,

    /**
     * 对角线：左上 & 右下为圆角
     */
    LEFT_DIAGONAL,

    /**
     * 对角线：右上 & 左下为圆角
     */
    RIGHT_DIAGONAL,

    /**
     * 上边为圆角（左上 + 右上）
     */
    TOP,

    /**
     * 下边为圆角（左下 + 右下）
     */
    BOTTOM,

    /**
     * 左边为圆角（左上 + 左下）
     */
    LEFT,

    /**
     * 右边为圆角（右上 + 右下）
     */
    RIGHT
}
