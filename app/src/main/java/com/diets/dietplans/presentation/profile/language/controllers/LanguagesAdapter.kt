package com.diets.dietplans.presentation.profile.language.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LanguagesAdapter(val arrayLanguages: Array<String>, selectedPosition: Int, val iSelectLang: ISelectLang) : RecyclerView.Adapter<LanguagesVH>() {

    private var currentSelectedItem = -1
    private var lastSelectedItem = -1

    init {
        currentSelectedItem = selectedPosition
        lastSelectedItem = selectedPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguagesVH {
        val inflater = LayoutInflater.from(parent.context)
        return LanguagesVH(inflater, parent, object : ISelectLang {
            override fun selectItem(position: Int) {
                iSelectLang.selectItem(position)
                selectNewItem(position)
            }
        })
    }

    private fun selectNewItem(position: Int) {
        lastSelectedItem = currentSelectedItem
        currentSelectedItem = position
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayLanguages.size
    }

    override fun onBindViewHolder(holder: LanguagesVH, position: Int) {
        holder.bind(arrayLanguages[position], currentSelectedItem == position, lastSelectedItem == currentSelectedItem)
    }
}