package com.diets.dietplans.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.contents_vh.view.*

class ContentsVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.contents_vh, viewGroup, false)) {

    fun bind(iContents: IContents) {
        itemView.fabInfo.setOnClickListener {
            iContents.moveTo(2)
        }
        itemView.fabBenefits.setOnClickListener {
            iContents.moveTo(3)
        }
        itemView.fabCons.setOnClickListener {
            iContents.moveTo(4)
        }
        itemView.fabMenu.setOnClickListener {
            iContents.moveTo(5)
        }
        itemView.fabResults.setOnClickListener {
            iContents.moveTo(6)
        }
        itemView.fabReviews.setOnClickListener {
            iContents.moveTo(7)
        }
    }
}