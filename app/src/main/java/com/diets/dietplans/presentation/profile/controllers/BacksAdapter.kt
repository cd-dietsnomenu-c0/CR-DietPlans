package com.diets.dietplans.presentation.profile.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BacksAdapter(val urls : Array<String>, val iBacks: IBacks) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BacksVH(LayoutInflater.from(parent.context), parent, iBacks)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BacksVH).bind(urls[position])
    }
}