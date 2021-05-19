package com.diets.dietplans.presentation.tracker.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.item_tracker_day.view.*

class DayVH(var layoutInflater: LayoutInflater, var viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_tracker_day, viewGroup, false)) {

    var listCV : List<CardView>
    var listTexts : List<TextView>
    var listAnim : List<LottieAnimationView>
    var listLoseImages : List<ImageView>
    var listCVMarkers : List<CardView>

    init {
        listCV = listOf( itemView.cvFirst, itemView.cvSecond, itemView.cvThird, itemView.cvFourth, itemView.cvFifth)
        listTexts = listOf( itemView.tvFirst, itemView.tvSecond, itemView.tvThird, itemView.tvFourth, itemView.tvFifth)
        listAnim = listOf( itemView.lavFirst, itemView.lavSecond, itemView.lavThird, itemView.lavFourth, itemView.lavFifth)
        listLoseImages = listOf( itemView.ivFirst, itemView.ivSecond, itemView.ivThird, itemView.ivFourth, itemView.ivFifth)
        listCVMarkers = listOf( itemView.cvMarkerFirst, itemView.cvMarkerSecond, itemView.cvMarkerThird, itemView.cvMarkerFourth, itemView.cvMarkerFifth)
    }

    fun bind(count: Int, dayState: Pair<MutableList<Int>, Int>, needPlayCompletedAnim: Boolean) {
        if (count < 5) itemView.cvFifth.visibility = View.GONE
        if (count < 4) itemView.cvFourth.visibility = View.GONE
        if (count < 3) itemView.cvThird.visibility = View.GONE
        if (count < 2) itemView.cvSecond.visibility = View.GONE
        setNumbers(adapterPosition )
        setStates(dayState.first, needPlayCompletedAnim, dayState.second)
        setMarkers(dayState.second)
    }

    private fun setMarkers(currentDayIndex: Int) {
        for (i in listCVMarkers.indices){
            if (i == currentDayIndex){
                listCVMarkers[i].visibility = View.VISIBLE
            }else {
                listCVMarkers[i].visibility = View.INVISIBLE
            }
        }
    }

    private fun setStates(dayState: MutableList<Int>, needPlayCompletedAnim: Boolean, currentDayIndex: Int) {
        for(i in dayState.indices){
            when(dayState[i]){
                DayConfig.UNUSED -> setUnusedState(i)
                DayConfig.LOSE -> setLoseState(i)
                DayConfig.CHECKED -> setCompletedState(i, needPlayCompletedAnim, currentDayIndex)
                //DayConfig.CURRENT -> setCurrent(i)
            }
        }
    }

    private fun setCompletedState(i: Int, needPlayCompletedAnim: Boolean, currentDayIndex: Int) {
        listLoseImages[i].visibility = View.INVISIBLE
        listAnim[i].visibility = View.VISIBLE
        if (currentDayIndex == i && needPlayCompletedAnim){
            listAnim[i].playAnimation()
        }else{
            listAnim[i].progress = 1.0f
        }
    }

    private fun setLoseState(i: Int) {
        listAnim[i].visibility = View.INVISIBLE
        listLoseImages[i].visibility = View.VISIBLE
    }

    private fun setUnusedState(i: Int) {
        listAnim[i].visibility = View.INVISIBLE
        listLoseImages[i].visibility = View.INVISIBLE
    }

    private fun setNumbers(offsetRate: Int) {
        for (i in listTexts.indices){
            listTexts[i].text = (i + 1 + offsetRate * 5).toString()
        }
    }

}