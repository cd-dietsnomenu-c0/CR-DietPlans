package com.diets.dietplans.presentation.tracker.controller.lives

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.item_lives.view.*

class LiveVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_lives, viewGroup, false)) {
    fun bind(stateLive: Boolean) {
        if (stateLive){
            Glide.with(itemView.context).load(R.drawable.ic_heart_full).into(itemView.ivLive)
        }else{
            Glide.with(itemView.context).load(R.drawable.ic_heart_empty).into(itemView.ivLive)

        }
    }
}