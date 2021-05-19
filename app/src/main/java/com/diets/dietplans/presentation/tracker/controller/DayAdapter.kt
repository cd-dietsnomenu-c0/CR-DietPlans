package com.diets.dietplans.presentation.tracker.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DayAdapter(var count: Int, val numbersLosesDays: MutableList<Int>, val currentDay : Int, var isDayCompleted : Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isNeedPlayCompletedAnim = false
    var residue = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DayVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        residue = count % 5
        var count = count / 5
        if (residue > 0){
            count += 1
        }
        return count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DayVH).bind(getSpanCount(position), getDayState(position), isNeedPlayCompletedAnim)
    }

    private fun getDayState(position: Int): Pair<MutableList<Int>, Int>{
        var currentDayIndex = DayConfig.NOT_HAVE_CURRENT_DAY
        var states = mutableListOf(DayConfig.UNUSED, DayConfig.UNUSED, DayConfig.UNUSED, DayConfig.UNUSED, DayConfig.UNUSED)
        var max = (position + 1) * 5 - 1
        var min = max - 4
        for (i in numbersLosesDays.indices){
            if (numbersLosesDays[i] in min..max){
                states[getIndex(numbersLosesDays[i])] = DayConfig.LOSE
            }
        }
        if (currentDay in (min)..max){
            for (i in 0 until getIndex(currentDay)){
                if (states[i] != DayConfig.LOSE){
                    states[i] = DayConfig.CHECKED
                }
            }
            if (isDayCompleted) states[getIndex(currentDay)] = DayConfig.CHECKED
            currentDayIndex = getIndex(currentDay)
        }else if (currentDay > max){
            for (i in states.indices){
                if (states[i] != DayConfig.LOSE){
                    states[i] = DayConfig.CHECKED
                }
            }
        }
        return Pair(states, currentDayIndex)
    }

    private fun getIndex(i: Int) : Int {
        return i % 5
    }

    private fun getSpanCount(position: Int): Int {
        if (position < itemCount - 1){
            return 5
        }else if (residue > 0){
            return residue
        }else {
            return 0
        }
    }

    fun notifyDayCompleted() {
        isDayCompleted = true
        isNeedPlayCompletedAnim = true
        notifyDataSetChanged()
    }
}