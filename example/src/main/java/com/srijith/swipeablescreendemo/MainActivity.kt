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
