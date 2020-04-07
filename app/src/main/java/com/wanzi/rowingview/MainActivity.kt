package com.wanzi.rowingview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.util.getItemView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = RowingAdapter(ArrayList<Adventure>().apply {
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

        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
            setDrawable(getDrawable(R.drawable.divider_item_decoration)!!)
        })
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val adapter = recyclerView.adapter as RowingAdapter
                adapter.move(50)
            }
        })
    }
}
