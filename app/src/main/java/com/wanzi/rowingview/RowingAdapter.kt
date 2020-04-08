package com.wanzi.rowingview

import android.graphics.Color
import android.util.Log
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
class RowingAdapter(data: MutableList<Adventure>) :
    BaseQuickAdapter<Adventure, BaseViewHolder>(R.layout.item_rowing, data) {

    private val ROWING_TAG = "RowingView"

    private var lastRowingView: RowingView? = null

    fun move(position: Int, progress: Float) {
        val rowingView = findRowingView(position)
        rowingView.move(progress)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else if (payloads.first() == 1000) {
            val cardView = holder.getView<CardView>(R.id.cardView)
            val rowingView = cardView.findViewWithTag<RowingView>(ROWING_TAG)
            if (rowingView != lastRowingView) {
                lastRowingView?.z = 0f
                rowingView.z = 1f
                lastRowingView = rowingView
            }
        }
    }


    override fun convert(holder: BaseViewHolder, item: Adventure) {
        val title = holder.getView<TextView>(R.id.title)
        title.text = if (item.type == Adventure.TYPE_GAME) "游戏" else "冒险"

        when {
            holder.adapterPosition == 0 -> {
                title.visibility = View.VISIBLE
            }
            item.type != data[holder.adapterPosition - 1].type -> {
                title.visibility = View.VISIBLE
            }
            else -> {
                title.visibility = View.GONE
            }
        }

        if (item.type == Adventure.TYPE_ADVENTURE) {
            val cardView = holder.getView<CardView>(R.id.cardView)
            val rowingView = RowingView(context).apply {
                setBackgroundColor(item.background?:Color.WHITE)
                setRiverColor(item.river ?: Color.WHITE)
                setPosition(holder.layoutPosition)
                tag = ROWING_TAG
            }
            cardView.addView(rowingView)
        }
    }

    private fun findRowingView(position: Int): RowingView {
        return getViewByPosition(position, R.id.cardView)?.findViewWithTag(ROWING_TAG)
            ?: throw Exception("传入的position异常:$position")
    }
}