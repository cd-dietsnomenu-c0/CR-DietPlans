package com.diets.dietplans.presentation.diets.list.old.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.R
import com.diets.dietplans.presentation.diets.list.ItemClick
import kotlinx.android.synthetic.main.head_vh.view.*

class HeadVH(inflater: LayoutInflater, viewGroup: ViewGroup, var itemClick: ItemClick) : RecyclerView.ViewHolder(inflater.inflate(R.layout.head_vh, viewGroup, false )) {
    fun bind() {
        //Glide.with(itemView.context).load(R.drawable.head_newdiets).into(itemView.ivBackGround)
        itemView.button.setOnClickListener {
            itemClick.newDietsClick()
        }
    }
}