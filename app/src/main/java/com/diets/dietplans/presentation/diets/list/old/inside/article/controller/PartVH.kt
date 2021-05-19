package com.diets.dietplans.presentation.diets.list.old.inside.article.controller

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.model.ItemOfSubsection
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.item_of_article.view.*

class PartVH(inflater: LayoutInflater, viewGroup: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_of_article, viewGroup, false)) {
    fun bind(itemOfSubsection: ItemOfSubsection) {
        itemView.tvBodyOfItemDescription.text = Html.fromHtml(itemOfSubsection.descriptionOfImage)
        itemView.tvBodyItemMainText.text = Html.fromHtml(itemOfSubsection.bodyOfText)
        if (itemOfSubsection.urlOfImage.equals("empty")){
            itemView.cvArticle.visibility = View.GONE
            itemView.ivBodyItemImage.visibility = View.GONE
        }else{
            Glide.with(itemView).load(itemOfSubsection.urlOfImage).into(itemView.ivBodyItemImage)
        }
    }
}