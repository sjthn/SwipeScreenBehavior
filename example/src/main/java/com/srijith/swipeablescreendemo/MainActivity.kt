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

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.srijith.swipeablescreens.SwipeCallback
import com.srijith.swipeablescreens.SwipeScreenBehavior
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutParams = constraintLayout.layoutParams as CoordinatorLayout.LayoutParams

        (layoutParams.behavior as SwipeScreenBehavior).setOnDismissListener(object : SwipeCallback {
            override fun dismissListener(parent: View?) {
                exitActivity(parent)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(getUserNames())
    }

    private fun getUserNames(): MutableList<String> {
        val mutableList = mutableListOf<String>()
        for (a in 0..29)
            mutableList.add(a, "User ${a + 1}")
        return mutableList
    }

    private fun exitActivity(parent: View?) {
        parent?.let {
            parent.animate().scaleY(0.2f).scaleX(0.2f).alpha(0f).withEndAction {
                parent.context?.let {
                    (parent.context as Activity).finish()
                }
            }
        }
    }
}
