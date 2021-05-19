package com.diets.dietplans.presentation.profile.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.item_back.view.*

class BacksVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val iBacks: IBacks) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_back, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }
    fun bind(imageUrl: String) {
        Glide.with(itemView.context).load(imageUrl).into(itemView.ivBack)
    }

    override fun onClick(v: View?) {
        iBacks.choiceBack(adapterPosition)
    }
}