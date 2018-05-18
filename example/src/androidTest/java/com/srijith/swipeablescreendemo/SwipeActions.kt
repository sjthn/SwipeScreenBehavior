/*
 * MIT License
 *
 * Copyright (c) 2018 Srijith
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.srijith.swipeablescreendemo

import android.support.design.widget.CoordinatorLayout
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewConfiguration
import org.hamcrest.Matcher

/**
 * Created by Srijith on 5/18/2018.
 */
fun scrollToBottom(): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "scroll to bottom"
        }

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
        }

        override fun perform(uiController: UiController?, view: View?) {
            val rv = view as RecyclerView
            rv.scrollToPosition(rv.adapter.itemCount - 1)
            uiController?.loopMainThreadUntilIdle()
        }

    }
}

const val swipeThreshold = 14
const val friction = 0.25f

enum class Translate {
    EQUAL_TO_THRESHOLD, GREATER_THAN_THRESHOLD, LESSER_THAN_THRESHOLD
}

fun translateTo(translate: Translate): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "translate to the given value"
        }

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
        }

        override fun perform(uiController: UiController?, view: View?) {
            val cl = view?.parent as CoordinatorLayout
            when (translate) {
                Translate.EQUAL_TO_THRESHOLD -> {
                    cl.onStartNestedScroll(view, view, View.SCROLL_AXIS_VERTICAL)
                    cl.onNestedScroll(
                        view, 0, 0, 0,
                        (-((cl.height / swipeThreshold) / friction)).toInt()
                    )
                    cl.onStopNestedScroll(view)
                }
                Translate.GREATER_THAN_THRESHOLD -> {
                    cl.onStartNestedScroll(view, view, View.SCROLL_AXIS_VERTICAL)
                    cl.onNestedScroll(
                        view, 0, 0, 0,
                        (-((cl.height / swipeThreshold + 1) / friction)).toInt()
                    )
                    cl.onStopNestedScroll(view)
                }
                else -> {
                    cl.onStartNestedScroll(view, view, View.SCROLL_AXIS_VERTICAL)
                    cl.onNestedScroll(
                        view, 0, 0, 0,
                        (-((cl.height / swipeThreshold - 1) / friction)).toInt()
                    )
                    cl.onStopNestedScroll(view)
                }
            }
            val duration = ViewConfiguration.getPressedStateDuration()
            uiController?.loopMainThreadForAtLeast(duration.toLong())
        }

    }
}

fun swipeDown(): ViewAction {
    return ViewActions.actionWithAssertions(
        GeneralSwipeAction(
            Swipe.SLOW,
            GeneralLocation.TOP_CENTER,
            GeneralLocation.CENTER,
            Press.FINGER
        )
    )
}

fun swipeUp(): ViewAction {
    return ViewActions.actionWithAssertions(
        GeneralSwipeAction(
            Swipe.SLOW,
            GeneralLocation.BOTTOM_CENTER,
            GeneralLocation.CENTER,
            Press.FINGER
        )
    )
}