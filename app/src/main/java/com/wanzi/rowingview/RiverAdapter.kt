package com.wanzi.rowingview

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *     author : WZ
 *     e-mail : 1253437499@qq.com
 *     time   : 2020/04/07
 *     desc   :
 *     version: 1.0
 */
class RiverAdapter(data: MutableList<Adventure>) :
    BaseQuickAdapter<Adventure, BaseViewHolder>(R.layout.item_rowing, data) {

    private val ROWING_TAG = "RowingView"

    private var lastRiverView: RiverView? = null

    fun move(position: Int, progress: Float) {
        val riverView = findRiverView(position)
        riverView.move(progress)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val cardView = holder.getView<CardView>(R.id.cardView)
            val riverView = cardView.findViewWithTag<RiverView>(ROWING_TAG)
            if (riverView != lastRiverView) {
                lastRiverView?.z = 0f
                lastRiverView?.rowing?.visibility = View.GONE
                riverView.z = 1f
                riverView.rowing.visibility = View.VISIBLE
                lastRiverView = riverView
            }
        }
    }


    override fun convert(holder: BaseViewHolder, item: Adventure) {
        val title = holder.getView<TextView>(R.id.title)
        title.text = if (item.type == Adventure.TYPE_GAME) "游戏" else "冒险"
        title.visibility = when {
            holder.adapterPosition == 0 -> View.VISIBLE
            item.type != data[holder.adapterPosition - 1].type -> View.VISIBLE
            else -> View.GONE
        }

        if (item.type == Adventure.TYPE_ADVENTURE) {
            val cardView = holder.getView<CardView>(R.id.cardView)
            val width = context.resources.displayMetrics.widthPixels.toFloat() - context.dp2px(32f)
            val height = cardView.layoutParams.height.toFloat()
            val riverView = RiverView(context).apply {
                setBackgroundColor(item.background ?: Color.WHITE)
                setRiverColor(item.river ?: Color.WHITE)
                setRiverWidth(100f)
                setRiverPath(getPath(holder.adapterPosition, width, height))
                tag = ROWING_TAG
            }

            cardView.addView(riverView)
        }
    }

    private fun findRiverView(position: Int): RiverView {
        return getViewByPosition(position, R.id.cardView)?.findViewWithTag(ROWING_TAG)
            ?: throw Exception("传入的position异常:$position")
    }
}