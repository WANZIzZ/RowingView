这个是模仿其他应用写的一个效果。

目标效果：

![a.gif](https://github.com/WANZIzZ/RowingView/blob/master/images/a.gif)

第一版效果：

![b.gif](https://github.com/WANZIzZ/RowingView/blob/master/images/b.gif)

目前效果：

![c.gif](https://github.com/WANZIzZ/RowingView/blob/master/images/c.gif)


主要实现功能比较简单，我说一下踩过的坑

* **第一次简单实现的时候，上下滑动次数太多会卡顿**

这个原因是在 ***onDraw*** 中每次都要重新设置 **mRiverPath** 导致的。

刚开始我写的比较简单， **mRiverPath** 是一条连续的直线，我直接通过判断 **Adapter** 的下标来设置 **mRiverPath** ： 

~~~
override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mRiverPath.reset()

        if (mPosition % 2 == 0) {
            mRiverPath.apply {
                lineTo(xxx, xxx)
                lineTo(xxx, xxx)
                lineTo(xxx, xxx)
            }
        } else {
            mRiverPath.apply {
                lineTo(xxx, xxx)
                lineTo(xxx, xxx)
                lineTo(xxx, xxx)
            }
        }
        canvas.drawPath(mRiverPath, mRiverPaint)

        ...
    }
~~~

后来我改成了在创建 **RiverView** 的时候，调用 ***setRiverPath*** 由外部传入，不用每次 ***onDraw*** 的时候重复添加路径就好了。

~~~
   /**
     * 设置小河路径
     */
    fun setRiverPath(path: Path) {
        mRiverPath = path
        ...
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(mRiverPath, mRiverPaint)

        ...
    }
~~~

* **在上下滑动列表的时候，小船移动不正常的问题**

小船的位置和方向我是通过 ***PathMeasure.getMatrix*** 来获取的，这个没问题，问题出在了 ***PathMeasure.setPath*** 时传入的 **Path** 。 

刚开始写的时候，我传入的 **Path** 是直线，类似

~~~
Path().apply {
            moveTo(50f, 50f)
            lineTo(50f, 100f)
            lineTo(100f, 100f)
        }
~~~

这种没任何问题。后来我优化的时候，把传入的 **Path** 改成了直接加圆弧，类似

~~~
Path().apply {
            moveTo(500f, 0f)
            lineTo(500f, 300f)
            addArc(300f, 200f, 500f, 400f, 0f, 90f)
        }
~~~

这种就有问题了。绘制完以后，看起来虽然是连续的一条线，但是 ***PathMeasure.length*** 这个返回的长度只是第一段直线的长度，在 ***PathMeasure.nextContour*** 以后，获取的长度才是第二段弧线的长度，而我们需要的是在第一次返回的时候就是直线加弧线的长度。
 
后来经过分析（网上查了也没查到，可能是姿势不对）和测试，我才发现，就算你实际绘制的线没问题，看起来是连续的，但如果你在添加后续 **Path** 的时候，调用到了 ***moveTo*** 以及类似的方法， **PathMeasure** 就会认为你后面的 **Path** 和前面的 **Path** 不是一起的，是分割的。

比如上面的 ***addArc*** ，这个实际上就是 ***arcTo(xxx, xxx, xxx, xxx, xxx, xxx, true)*** ，就等于在绘制后面的圆弧时先是调用 ***moveTo*** 将起点移动过去以后才开始绘制的，所以 **PathMeasure** 就会认为前面的直线和后面的圆弧是分割开的。只要我们改成：

~~~
Path().apply {
            moveTo(500f, 0f)
            lineTo(500f, 300f)
            arcTo(300f, 200f, 500f, 400f, 0f, 90f, false)
        }
~~~

就没问题了。感兴趣的老哥可以去试一试。

还有一个，在绘制小船的时候，起点是小船的左上角，这个是不对的，应该少移动半个小船的大小，即将小船往左上移动一下。由于我刚开始用的是 ***PathMeasure.getMatrix*** ，所以后面应该再加一个 ***mMatrix.preTranslate*** ，注意：是 ***preTranslate*** ，而不是 ***postTranslate*** ，这个一定要注意！我在这里就被坑了一次。

* **小船滑不到底的问题**

我是通过 **RecyclerView** 的 ***onScrolled*** ，先拿到总滑动的距离 ***recyclerView.computeVerticalScrollOffset***，然后再计算每个 **itemview** 应该滑动多少距离（这个在代码中有），最后再计算出每个 **itemview** 中小船的位置。 这个流程没问题，但是实际使用的时候，小船总是滑不到底部，老是差一点距离。后来我就想是不是小船实际的滑动距离和理论滑动距离不一样，最后发现是 ***recyclerView.computeVerticalScrollOffset*** 这个方法有问题，这个拿到的距离不精确，这个我也在网上找到了文章，这里我就不再多说了。
