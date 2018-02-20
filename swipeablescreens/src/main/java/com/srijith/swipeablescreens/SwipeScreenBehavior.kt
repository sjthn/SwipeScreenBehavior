package com.srijith.swipeablescreens

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

/**
 * Created by Srijith on 2/8/2018.
 */
open class SwipeScreenBehavior<V : View>(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<V>(context, attrs) {

    private val friction = 0.25f
    private val swipeThreshold = 14

    private var touchStartY = 0f
    private var totalDrag = 0f
    private var isDragDown = false
    private var isDragUp = false
    private var isDismissed = false
    private lateinit var dismissCallback: SwipeCallback

    constructor() : this(null, null)

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout?,
        child: V,
        ev: MotionEvent?
    ): Boolean {
        return false
    }

    override fun onTouchEvent(parent: CoordinatorLayout?, child: V, ev: MotionEvent?): Boolean {
        if (isDismissed) return false

        var consumed = false
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                consumed = true
                touchStartY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                parent?.let {
                    totalDrag = -(touchStartY - ev.y)

                    dragAndScaleView(totalDrag, parent)
                }
            }
            MotionEvent.ACTION_UP -> {
                parent?.let {
                    if (shouldDismissScreen(parent)) {
                        isDismissed = true
                        dismissCallback.dismissListener(parent)
                    } else {
                        if (parent.y != 0f) {
                            parent.animate().translationY(0f).scaleX(1f).scaleY(1f)
                        }
                    }
                }
                consumed = false
                resetValues()
            }
            else -> {
                consumed = false
                resetValues()
            }
        }
        return consumed
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && type == ViewCompat.TYPE_TOUCH && !isDismissed
    }

    override fun onNestedPreScroll(
        parent: CoordinatorLayout,
        child: V,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        // Called when the view is already in dragged state.
        if (dy != 0 && (isDragUp || isDragDown)) {
            dragAndScaleNestedScroller(dy, parent)
            consumed[1] = dy
        }
    }

    override fun onNestedScroll(
        parent: CoordinatorLayout,
        child: V,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (dyUnconsumed == 0) return
        dragAndScaleNestedScroller(dyUnconsumed, parent)
    }

    override fun onStopNestedScroll(
        parent: CoordinatorLayout,
        child: V,
        target: View,
        type: Int
    ) {
        totalDrag = 0f
        if (shouldDismissScreen(parent)) {
            isDismissed = true
            dismissCallback.dismissListener(parent)
        } else {
            if (parent.y != 0f) {
                parent.animate().translationY(0f).scaleX(1f).scaleY(1f)
            }
        }
        isDragDown = false
        isDragUp = false
    }

    fun setOnDismissListener(callback: SwipeCallback) {
        dismissCallback = callback
    }

    private fun dragAndScaleNestedScroller(dy: Int, parent: CoordinatorLayout) {
        if (dy < 0) isDragDown = true else isDragUp = true

        totalDrag += -(dy) * friction
        parent.translationY = totalDrag
        parent.scaleX = 0.95f
        parent.scaleY = 0.95f
    }

    private fun dragAndScaleView(dy: Float, parent: CoordinatorLayout) {
        parent.translationY = dy * friction
        parent.scaleX = 0.95f
        parent.scaleY = 0.95f
    }

    private fun shouldDismissScreen(parent: View?): Boolean {
        parent?.let {
            if (abs(parent.y) > parent.height / swipeThreshold) {
                return true
            }
        }
        return false
    }

    private fun resetValues() {
        touchStartY = 0f
        totalDrag = 0f
    }
}

interface SwipeCallback {
    fun dismissListener(parent: View?)
}