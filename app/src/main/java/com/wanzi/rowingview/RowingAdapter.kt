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
class RowingAdapter(data: MutableList<Adventure>) :
    BaseQuickAdapter<Adventure, BaseViewHolder>(R.layout.item_rowing, data) {

    private lateinit var rowingView: RowingView

    fun move(progress: Int) {
        rowingView.move(progress)
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
            rowingView = RowingView(context)
            rowingView.setBkgColor(item.background ?: Color.WHITE)
            rowingView.setRiverColor(item.river ?: Color.WHITE)
            rowingView.setPosition(holder.layoutPosition)
            cardView.addView(rowingView)
        }
    }


}