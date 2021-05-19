package com.diets.dietplans.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.vh_benefits.view.*

class BenefitsVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.vh_benefits, viewGroup, false)) {
    fun bind(benefitsTitle: String, benefits: List<String>) {
        itemView.tvBenTitle.text = benefitsTitle
        itemView.tvBenText.text = getBenefitsText(benefits)
    }

    private fun getBenefitsText(benefits: List<String>): String {
        var allBenefits = ""
        for (i in benefits.indices) {
            if (benefits[i] == ""){
                break
            }
            allBenefits += (i + 1).toString() + ". " + benefits[i] + "\n"
        }
        return allBenefits
    }
}