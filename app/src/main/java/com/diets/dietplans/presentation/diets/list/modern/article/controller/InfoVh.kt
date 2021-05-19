package com.diets.dietplans.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.vh_info.view.*

class InfoVh(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.vh_info, viewGroup, false)) {
    fun bind(title: String, introduction: String) {
        itemView.tvInfoText.text = introduction
    }
}