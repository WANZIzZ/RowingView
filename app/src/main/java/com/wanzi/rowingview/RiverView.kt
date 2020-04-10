package com.wanzi.rowingview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.atan2

/**
 *     author : 丸子
 *     e-mail : 1253437499@qq.com
 *     time   : 2020/04/08
 *     desc   :
 *     version: 1.0
 */
class RiverView : ViewGroup {

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
     * 小船（前期用箭头替代）
     */
    private val mArrow = BitmapFactory.decodeResource(resources, R.drawable.arrow)

    /**
     * 真实的小船
     */
    val rowing = RowingView(context)

    private val mPathMeasure = PathMeasure()
    private val mPos = FloatArray(2)
    private val mTan = FloatArray(2)
    private lateinit var mMatrix: Matrix

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        setWillNotDraw(false)

        rowing.layout(0, 0, 200, 200)
    }

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
     * 移动小船
     */
    fun move(progress: Float) {
        if (!this::mMatrix.isInitialized) {
            mMatrix = Matrix()
            addView(rowing)
        }

        mMatrix.reset()

        mPathMeasure.getMatrix(
            (progress) * mPathMeasure.length,
            mMatrix,
            PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG
        )

        mPathMeasure.getPosTan((progress) * mPathMeasure.length, mPos, mTan)

        mMatrix.preTranslate(-mArrow.width / 2.toFloat(), -mArrow.height / 2.toFloat())

        invalidate()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(mRiverPath, mRiverPaint)

        if (this::mMatrix.isInitialized) {
            rowing.let {
                it.translationX = mPos[0] - it.width / 2
                it.translationY = mPos[1] - it.height / 2
                it.rotation = atan2(mTan[1], mTan[0]) * 180 / Math.PI.toFloat() + 180
            }
            //    canvas.drawBitmap(mArrow, mMatrix, mRiverPaint)
        }
    }
}