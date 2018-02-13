package com.srijith.swipeablescreendemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_user_list.view.*

/**
 * Created by Srijith on 2/8/2018.
 */
class RecyclerViewAdapter(private var items: MutableList<String>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.layout_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.initViews(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun initViews(name: String) {
        view.textView.text = name
    }
}