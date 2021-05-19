package com.diets.dietplans.presentation.tracker.controller.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(var listEat : List<Int>, val iMenu: IMenu) : RecyclerView.Adapter<MenuVH>() {

    init {
        if (listEat.isEmpty()){
            iMenu.completeDay()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuVH {
        return MenuVH(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return listEat.size
    }

    override fun onBindViewHolder(holder: MenuVH, position: Int) {
        holder.bind(listEat[position])
    }

    fun notify(type: Int) {
        listEat = listEat - listOf<Int>(type)
        notifyDataSetChanged()
        if (listEat.isEmpty()){
            iMenu.completeDay()
        }
    }
}