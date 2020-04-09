package com.wanzi.rowingview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *     author : WZ
 *     e-mail : 1253437499@qq.com
 *     time   : 2020/04/09
 *     desc   :
 *     version: 1.0
 */
class RiverLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    private val heightMap = HashMap<Int, Int>()

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)

        for (i in 0 until childCount) {
            val childAt = getChildAt(i) ?: throw KotlinNullPointerException()
            heightMap[getPosition(childAt)] = childAt.height
        }
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        if (childCount == 0) return 0

        val findFirstVisibleItemPosition = findFirstVisibleItemPosition()
        val findViewByPosition =
            findViewByPosition(findFirstVisibleItemPosition) ?: throw KotlinNullPointerException()

        var y = -findViewByPosition.y.toInt()
        for (i in 0 until findFirstVisibleItemPosition) {
            val num = heightMap.get(i)
            y += num ?: 0
        }
        return y
    }
}