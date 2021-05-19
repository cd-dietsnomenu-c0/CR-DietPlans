package com.diets.dietplans.presentation.diets.list.modern.article.controller.managers

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LayoutManagerTopScroll(context: Context) : LinearLayoutManager(context, VERTICAL, false) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val scroller = TopSnappedScroller(recyclerView!!.context)
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }
}