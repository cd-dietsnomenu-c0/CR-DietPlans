package com.diets.dietplans.presentation.diets.list.modern.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.item_head.view.*

class HeadVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_head, viewGroup, false)) {


    fun bind() {
        Glide.with(itemView.context).load("https://i.ibb.co/ZVQVk62/head01.jpg").into(itemView.ivHeadBack)
        //itemView.cvBack.setBackgroundResource(R.drawable.shape_dukan)
    }


}