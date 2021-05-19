package com.diets.dietplans.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.model.interactive.Review
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.vh_review.view.*

class ReviewVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.vh_review, viewGroup, false)) {
    fun bind(review: Review) {
        Glide.with(itemView.context).load(review.avatar).into(itemView.ivAvatar)
        itemView.tvNameReview.text = review.name
        itemView.tvReviewText.text = review.text
    }
}