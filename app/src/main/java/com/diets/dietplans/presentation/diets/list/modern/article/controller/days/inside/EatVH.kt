package com.diets.dietplans.presentation.diets.list.modern.article.controller.days.inside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.model.interactive.Eat
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.item_eat.view.*

class EatVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_eat, viewGroup, false)) {

    init {
        itemView.cvEatCard.setBackgroundResource(R.drawable.eat_card_shape)
    }

    fun bind(eat: Eat) {
        Glide.with(itemView.context).load(getImage(eat.type)).into(itemView.ivTitleImage)
        itemView.flBackEatCard.setBackgroundResource(getBackground(eat.type))
        itemView.tvEatText.text = eat.text
        itemView.tvEatTitle.text = itemView.resources.getStringArray(R.array.eating_names)[eat.type]
    }

    fun getImage(typeEat : Int) : Int{
       return itemView.resources.obtainTypedArray(R.array.eats_icons).getResourceId(typeEat, -1)
    }

    fun getBackground(typeEat : Int) : Int {
        return itemView.resources.obtainTypedArray(R.array.eats_gradients).getResourceId(typeEat, -1)
    }
}