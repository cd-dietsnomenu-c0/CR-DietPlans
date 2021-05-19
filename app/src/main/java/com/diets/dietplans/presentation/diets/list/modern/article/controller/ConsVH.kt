package com.diets.dietplans.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.vh_cons.view.*

class ConsVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.vh_cons, viewGroup, false)) {
    fun bind(consTitle: String, cons: List<String>) {
        itemView.tvConsTitle.text = consTitle
        itemView.tvConsText.text = getAllCons(cons)
    }

    private fun getAllCons(cons: List<String>): String {
        var allCons = ""
        for (i in cons.indices) {
            if (cons[i] == ""){
                break
            }
            allCons += (i + 1).toString() + ". " + cons[i] + "\n"
        }
        return allCons
    }
}