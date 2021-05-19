package com.diets.dietplans.presentation.diets.list.modern.article.controller.days

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.model.interactive.DietDay

class DietDayAdapter(var days: List<DietDay>) : RecyclerView.Adapter<DietDayVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietDayVH {
        val inflater = LayoutInflater.from(parent.context)
        return DietDayVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: DietDayVH, position: Int) {
        holder.bind(days[position])
    }
}