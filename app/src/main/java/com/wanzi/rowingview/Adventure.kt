package com.wanzi.rowingview

/**
 *     author : WZ
 *     e-mail : 1253437499@qq.com
 *     time   : 2020/04/07
 *     desc   :
 *     version: 1.0
 */
data class Adventure(val type: Int, val background: Int?, val river: Int?) {

    companion object {
        val TYPE_GAME = 0
        val TYPE_ADVENTURE = 1
    }
}