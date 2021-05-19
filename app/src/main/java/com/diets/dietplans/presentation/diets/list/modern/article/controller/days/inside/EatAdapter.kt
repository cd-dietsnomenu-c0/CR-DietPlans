package com.diets.dietplans.presentation.diets.list.modern.article.controller.days.inside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.model.interactive.Eat

class EatAdapter(var eatList: List<Eat>) : RecyclerView.Adapter<EatVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EatVH {
        val inflater = LayoutInflater.from(parent.context)
        return EatVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return eatList.size
    }

    override fun onBindViewHolder(holder: EatVH, position: Int) {
        holder.bind(eatList[position])
    }

}