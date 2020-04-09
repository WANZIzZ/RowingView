package com.wanzi.rowingview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_river_test.*

class RiverTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_river_test)

        val width = resources.displayMetrics.widthPixels.toFloat() - dp2px(32f)
        val height = cardView.layoutParams.height.toFloat()

        val riverView = RiverView(this).apply {
            setBackgroundColor(Color.parseColor("#4bab64"))
            setRiverColor(Color.parseColor("#5ec87b"))
            setRiverWidth(100f)
            setRiverPath(getPath(0, width, height))
        }

        cardView.addView(riverView)

        //  riverView.move(0f)

        /*btn_up.setOnClickListener {
            riverView.move(-0.1f)
        }

        btn_down.setOnClickListener {
            riverView.move(0.1f)
        }*/
    }
}
