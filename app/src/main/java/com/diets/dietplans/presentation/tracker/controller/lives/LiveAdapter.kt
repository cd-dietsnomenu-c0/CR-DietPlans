package com.diets.dietplans.presentation.tracker.controller.lives

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LiveAdapter(val lives : Int, val loses : Int) : RecyclerView.Adapter<LiveVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveVH {
        return LiveVH(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return lives
    }

    override fun onBindViewHolder(holder: LiveVH, position: Int) {
        holder.bind(getStateLive(position))
    }

    private fun getStateLive(position: Int): Boolean {
        var livesLeft = lives - loses
        return position < livesLeft
    }
}