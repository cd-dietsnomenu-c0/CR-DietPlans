package com.diets.dietplans.presentation.tracker.controller.eats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.model.interactive.Eat
import com.diets.dietplans.common.DBHolder

class EatAdapter(var listEat : List<Eat>, val breakfastState : Int, val lunchState : Int,
                 val dinnerState : Int, val snackState : Int,val snack2State : Int, val iEat : IEat) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EatVH(inflater, parent, iEat)
    }

    override fun getItemCount(): Int {
        return listEat.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentEatState = DBHolder.NOT_CHECKED
        when(listEat[position].type){
            0 -> currentEatState = breakfastState
            1 -> currentEatState = lunchState
            2 -> currentEatState = dinnerState
            3 -> currentEatState = snackState
            4 -> currentEatState = snack2State
        }
        (holder as EatVH).bind(listEat[position], currentEatState)
    }
}