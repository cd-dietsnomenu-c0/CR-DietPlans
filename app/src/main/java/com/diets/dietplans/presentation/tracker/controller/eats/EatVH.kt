package com.diets.dietplans.presentation.tracker.controller.eats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.dietplans.model.interactive.Eat
import com.diets.dietplans.R
import com.diets.dietplans.common.DBHolder
import kotlinx.android.synthetic.main.item_eat.view.cvEatCard
import kotlinx.android.synthetic.main.item_eat.view.flBackEatCard
import kotlinx.android.synthetic.main.item_eat.view.ivTitleImage
import kotlinx.android.synthetic.main.item_eat.view.tvEatText
import kotlinx.android.synthetic.main.item_eat.view.tvEatTitle
import kotlinx.android.synthetic.main.item_eat_tracker.view.*

class EatVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val iEat: IEat) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_eat_tracker, viewGroup, false)) {

    init {
        itemView.cvEatCard.setBackgroundResource(R.drawable.eat_card_shape)
    }

    fun bind(eat: Eat, currentEatState: Int) {
        Glide.with(itemView.context).load(getImage(eat.type)).into(itemView.ivTitleImage)
        itemView.flBackEatCard.setBackgroundResource(getBackground(eat.type))
        itemView.tvEatText.text = eat.text
        itemView.tvEatTitle.text = itemView.resources.getStringArray(R.array.eating_names)[eat.type]
        if (currentEatState == DBHolder.CHECKED){
            itemView.fabAdd.visibility = View.INVISIBLE
            itemView.lavCheck.visibility = View.VISIBLE
            itemView.lavCheck.progress = 1.0f
        }else {
            itemView.fabAdd.visibility = View.VISIBLE
            itemView.lavCheck.visibility = View.INVISIBLE
            itemView.fabAdd.setImageResource(getImagePlus(eat.type))
            itemView.fabAdd.setOnClickListener {
                itemView.fabAdd.visibility = View.GONE
                itemView.lavCheck.visibility = View.VISIBLE
                itemView.lavCheck.playAnimation()
                iEat.checkEat(eat.type)
            }
        }
    }

    private fun getImage(typeEat: Int): Int {
        return itemView.resources.obtainTypedArray(R.array.eats_icons).getResourceId(typeEat, -1)
    }

    private fun getImagePlus(typeEat: Int): Int {
        return itemView.resources.obtainTypedArray(R.array.pluses).getResourceId(typeEat, -1)
    }

    private fun getBackground(typeEat: Int): Int {
        return itemView.resources.obtainTypedArray(R.array.eats_gradients).getResourceId(typeEat, -1)
    }
}