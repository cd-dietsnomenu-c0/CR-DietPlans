package com.diets.dietplans.presentation.tracker.controller.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.item_tracker_menu.view.*

class MenuVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_tracker_menu, viewGroup, false)) {


    fun bind(type: Int) {
        Glide.with(itemView.context).load(getImage(type)).into(itemView.ivMenuIcon)
    }

    private fun getImage(typeEat: Int): Int {
        return itemView.resources.obtainTypedArray(R.array.eats_icons).getResourceId(typeEat, -1)
    }


}