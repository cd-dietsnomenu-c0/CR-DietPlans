package com.diets.dietplans.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.head_article_vh.view.*

class HeadVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.head_article_vh, viewGroup, false)) {
    fun bind(title: String, shortIntroduction: String, mainImage: String) {
        Glide.with(itemView.context).load(mainImage).into(itemView.ivCollapsing)
        itemView.tvHead.text = title
        itemView.tvSubtitle.text = shortIntroduction
    }
}