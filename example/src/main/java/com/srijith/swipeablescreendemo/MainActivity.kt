package com.srijith.swipeablescreendemo

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.srijith.swipeablescreens.SwipeScreenBehavior
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutParams = constraintLayout.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = object : SwipeScreenBehavior<View>() {
            override fun dismissListener(parent: View?) {
                super.dismissListener(parent)
            }
        }
        layoutParams.behavior = behavior

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(getUserNames())
    }

    private fun getUserNames(): MutableList<String> {
        val mutableList = mutableListOf<String>()
        for (a in 0..29)
            mutableList.add(a, "User ${a + 1}")
        return mutableList
    }
}
