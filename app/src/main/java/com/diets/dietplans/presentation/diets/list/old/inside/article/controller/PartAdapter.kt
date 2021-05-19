package com.diets.dietplans.presentation.diets.list.old.inside.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.model.ItemOfSubsection

class PartAdapter(var list: ArrayList<ItemOfSubsection>): RecyclerView.Adapter<PartVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartVH {
        val inflater = LayoutInflater.from(parent.context)
        return PartVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PartVH, position: Int) {
        holder.bind(list[position])
    }
}