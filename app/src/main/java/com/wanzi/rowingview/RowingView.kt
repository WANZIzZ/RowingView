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
     * 小河的画笔
     */
    private val mRiverPaint = Paint().apply {
        style = Paint.Style.STROKE
        pathEffect = CornerPathEffect(100f)
        strokeWidth = 100f
    }

    val mRowingView = BitmapFactory.decodeResource(resources, R.drawable.arrow)

    /**
     * 小河的路径
     */
    private val mRiverPath = Path()

    /**
     * 当前是第几个item
     */
    private var mPosition: Int = 0

    private var mProgress = 0f

    private val mPathMeasure = PathMeasure()

    private val mMatrix = Matrix()

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    fun setRiverColor(color: Int) {
        mRiverPaint.color = color
    }

    fun setPosition(position: Int) {
        this.mPosition = position
    }

    fun move(progress: Float) {
        this.mProgress = progress
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawRiver(canvas)
        moveRowing(canvas)
    }

    private fun drawRiver(canvas: Canvas) {
        if (this.mPosition % 2 == 1) {
            mRiverPath.moveTo((width * 4 / 5).toFloat(), 0f)
            mRiverPath.rLineTo(0f, (height * 2 / 3.toFloat()))
            mRiverPath.rLineTo(-(width * 3 / 5).toFloat(), 0f)
            mRiverPath.rLineTo(0f, (height / 3).toFloat())
        } else {
            mRiverPath.moveTo((width * 1 / 5).toFloat(), 0f)
            mRiverPath.rLineTo(0f, (height * 2 / 3.toFloat()))
            mRiverPath.rLineTo((width * 3 / 5).toFloat(), 0f)
            mRiverPath.rLineTo(0f, (height / 3).toFloat())
        }
        canvas.drawPath(mRiverPath, mRiverPaint)
    }

    private fun moveRowing(canvas: Canvas) {
        if (mProgress != 0f) {
            mPathMeasure.setPath(mRiverPath, false)
            mPathMeasure.getMatrix(
                (1 - mProgress) * mPathMeasure.length,
                mMatrix,
                PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG
            )
            mMatrix.postTranslate(
                mRowingView.width / 2.toFloat(),
                -mRowingView.height / 2.toFloat()
            )
            canvas.drawBitmap(mRowingView, mMatrix, mRiverPaint)
        }
    }

}