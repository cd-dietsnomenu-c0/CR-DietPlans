package com.diets.dietplans.presentation.profile.language.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.languages_item.view.*

class LanguagesVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val iSelectLang: ISelectLang) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.languages_item, viewGroup, false)), View.OnClickListener {


    fun bind(lang: String, isSelected : Boolean, isDefaultSelected : Boolean) {
        itemView.tvLang.text = lang
        if (isSelected){
            itemView.tvLang.isSelected = true
            itemView.llLang.isSelected = true
            if (isDefaultSelected){
                itemView.lavTick.progress = 1.0f
            }else{
                itemView.lavTick.progress = 0.0f
                itemView.lavTick.playAnimation()
            }
        }else{
            itemView.tvLang.isSelected = false
            itemView.llLang.isSelected = false
            itemView.lavTick.pauseAnimation()
            itemView.lavTick.progress = 0.0f
        }
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        iSelectLang.selectItem(adapterPosition)
    }
}