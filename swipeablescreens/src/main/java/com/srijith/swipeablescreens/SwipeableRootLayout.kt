package com.srijith.swipeablescreens

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet

/**
 * Created by Srijith on 2/8/2018.
 */
class SwipeableRootLayout : CoordinatorLayout {
    constructor(c: Context?) : this(c, null)

    constructor(c: Context?, attr: AttributeSet?) : this(c, attr, 0)

    constructor(c: Context?, attr: AttributeSet?, defStyleAttr: Int) : super(
        c,
        attr,
        defStyleAttr
    )
}