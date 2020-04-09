package com.wanzi.rowingview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *     author : 丸子
 *     e-mail : 1253437499@qq.com
 *     time   : 2020/04/08
 *     desc   :
 *     version: 1.0
 */
class RiverView : View {

    /**
     * 小河路径
     */
    private var mRiverPath = Path()

    /**
     * 小河画笔
     */
    private val mRiverPaint = Paint().apply {
        style = Paint.Style.STROKE
    }

    /**
     * 小船
     */
    private val mRowing = BitmapFactory.decodeResource(resources, R.drawable.arrow)

    private val mPathMeasure = PathMeasure()
    private lateinit var mMatrix: Matrix

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    /**
     * 设置小河路径
     */
    fun setRiverPath(path: Path) {
        mRiverPath = path
        mPathMeasure.setPath(mRiverPath, false)
    }

    /**
     * 设置小河颜色
     */
    fun setRiverColor(color: Int) {
        mRiverPaint.color = color
    }

    /**
     * 设置小河宽度
     */
    fun setRiverWidth(width: Float) {
        mRiverPaint.strokeWidth = width
    }

    /**
     *
     */
    fun move(progress: Float) {
        if (!this::mMatrix.isInitialized) {
            mMatrix = Matrix()
        }

        mMatrix.reset()

        mPathMeasure.getMatrix(
            (progress) * mPathMeasure.length,
            mMatrix,
            PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG
        )

        mMatrix.preTranslate(-mRowing.width / 2.toFloat(), -mRowing.height / 2.toFloat())

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(mRiverPath, mRiverPaint)

        if (this::mMatrix.isInitialized) {
            canvas.drawBitmap(mRowing, mMatrix, mRiverPaint)
        }
    }
}