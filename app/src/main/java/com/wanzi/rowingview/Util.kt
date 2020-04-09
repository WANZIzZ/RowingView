package com.wanzi.rowingview

import android.content.Context
import android.graphics.Path

/**
 *     author : 丸子
 *     e-mail : 1253437499@qq.com
 *     time   : 2020/04/08
 *     desc   :
 *     version: 1.0
 */
fun Context.dp2px(dp: Float): Float {
    return dp * resources.displayMetrics.density + 0.5f
}

/**
 * 因为一共就两种路径，所以用下标来区分
 */
fun getPath(position: Int, width: Float, height: Float): Path {
    val radius = 100f

    val firstLineWidth: Float
    val firstLineHeight = height / 2

    val lastLineWidth: Float

    if (position % 2 == 0) {
        firstLineWidth = width * 7 / 8
        lastLineWidth = width / 8
    } else {
        firstLineWidth = width / 8
        lastLineWidth = width * 7 / 8
    }

    return Path().apply {
        // 1.竖线
        moveTo(firstLineWidth, 0f)
        lineTo(firstLineWidth, firstLineHeight)

        if (position % 2 == 0) {
            // 2.圆弧
            arcTo(
                firstLineWidth - 2 * radius,
                firstLineHeight - radius,
                firstLineWidth,
                firstLineHeight + radius,
                0f,
                90f,
                false
            )

            // 3.横线
            rLineTo(lastLineWidth - firstLineHeight + 2 * radius, 0f)

            // 4.圆弧
            arcTo(
                lastLineWidth,
                firstLineHeight + radius,
                lastLineWidth + 2 * radius,
                firstLineHeight + 3 * radius,
                -90f,
                -90f,
                false
            )
        } else {
            // 2.圆弧
            arcTo(
                firstLineWidth,
                firstLineHeight - radius,
                firstLineWidth + 2 * radius,
                firstLineHeight + radius,
                -180f,
                -90f,
                false
            )

            // 3.横线
            rLineTo(lastLineWidth - firstLineWidth - 2 * radius, 0f)

            // 4.圆弧
            arcTo(
                lastLineWidth - 2 * radius,
                firstLineHeight + radius,
                lastLineWidth,
                firstLineHeight + 3 * radius,
                -90f,
                90f,
                false
            )
        }

        // 5.竖线
        rLineTo(0f, height / 2 - 2 * radius)
    }
}