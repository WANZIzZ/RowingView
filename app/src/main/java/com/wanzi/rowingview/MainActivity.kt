package com.wanzi.rowingview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = RiverAdapter(ArrayList<Adventure>().apply {
        add(Adventure(Adventure.TYPE_GAME, null, null))
        add(
            Adventure(
                Adventure.TYPE_ADVENTURE,
                Color.parseColor("#4bab64"),
                Color.parseColor("#5ec87b")
            )
        )
        add(
            Adventure(
                Adventure.TYPE_ADVENTURE,
                Color.parseColor("#1578b9"),
                Color.parseColor("#40a4e9")
            )
        )
        add(
            Adventure(
                Adventure.TYPE_ADVENTURE,
                Color.parseColor("#a78137"),
                Color.parseColor("#ddc367")
            )
        )
        add(
            Adventure(
                Adventure.TYPE_ADVENTURE,
                Color.parseColor("#f99e2f"),
                Color.parseColor("#fdd675")
            )
        )
        add(
            Adventure(
                Adventure.TYPE_ADVENTURE,
                Color.parseColor("#f42d00"),
                Color.parseColor("#fd6d49")
            )
        )
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.layoutManager = RiverLinearLayoutManager(this)
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 总移动距离
                val computeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset()
                // 平均每个item滑动距离
                val scrollDistance = (dp2px((240f + 16f) * 6 + 48f) - recyclerView.height) / 5
                val progress = computeVerticalScrollOffset % scrollDistance / scrollDistance
                val position = 1 + (computeVerticalScrollOffset / scrollDistance).toInt()
                val adapter = recyclerView.adapter as RiverAdapter
                recyclerView.post {
                    adapter.move(position, progress)
                    adapter.notifyItemChanged(position, 1)
                }
            }
        })
    }
}
