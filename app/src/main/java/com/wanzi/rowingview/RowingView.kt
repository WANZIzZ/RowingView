package com.wanzi.rowingview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 *     author : WZ
 *     e-mail : 1253437499@qq.com
 *     time   : 2020/04/07
 *     desc   :
 *     version: 1.0
 */
class RowingView : View {

    /**
     * 默认背景色 白色
     */
    private var bgColor = Color.WHITE

    /**
     * 小河的画笔
     */
    private val riverPaint = Paint().apply {
        style = Paint.Style.STROKE
        pathEffect = CornerPathEffect(100f)
        strokeWidth = 100f
    }

    /**
     * 小河的路径
     */
    private val riverPath = Path()

    /**
     * 当前是第几个item
     */
    private var position: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    fun setBkgColor(color: Int) {
        this.bgColor = color
    }

    fun setRiverColor(color: Int) {
        riverPaint.color = color
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun move(progress: Int) {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBg(canvas)
        drawRiver(canvas)
    }

    private fun drawBg(canvas: Canvas) {
        canvas.drawColor(bgColor)
    }

    private fun drawRiver(canvas: Canvas) {
        if (this.position % 2 == 1) {
            riverPath.moveTo((width * 4 / 5).toFloat(), 0f)
            riverPath.rLineTo(0f, (height * 2 / 3.toFloat()))
            riverPath.rLineTo(-(width * 3 / 5).toFloat(), 0f)
            riverPath.rLineTo(0f, (height / 3).toFloat())
        } else {
            riverPath.moveTo((width * 1 / 5).toFloat(), 0f)
            riverPath.rLineTo(0f, (height * 2 / 3.toFloat()))
            riverPath.rLineTo((width * 3 / 5).toFloat(), 0f)
            riverPath.rLineTo(0f, (height / 3).toFloat())
        }
        canvas.drawPath(riverPath, riverPaint)
    }

}