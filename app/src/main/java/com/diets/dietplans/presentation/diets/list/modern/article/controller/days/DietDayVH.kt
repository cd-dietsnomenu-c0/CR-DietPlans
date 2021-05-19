package com.diets.dietplans.presentation.diets.list.modern.article.controller.days

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.model.interactive.DietDay
import com.diets.dietplans.R
import com.diets.dietplans.presentation.diets.list.modern.article.controller.days.inside.EatAdapter
import kotlinx.android.synthetic.main.item_diet_day.view.*

class DietDayVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_diet_day, viewGroup, false)) {

    init {
        itemView.rvDays.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun bind(day: DietDay) {
        itemView.tvDayTitle.text = day.title
        itemView.rvDays.adapter = EatAdapter(day.eats)
    }
}