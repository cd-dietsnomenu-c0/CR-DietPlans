package com.diets.dietplans.presentation.diets.list.modern.article.controller.managers

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

class TopSnappedScroller(context: Context) : LinearSmoothScroller(context) {

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }
}