package com.diets.dietplans.presentation.calculators.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.presentation.diets.controller.ADVH
import com.yandex.mobile.ads.nativeads.NativeAd
import java.lang.annotation.Native
import java.util.ArrayList

class CalculatingAdapter(val titles: Array<String>,
                         val descriptions: Array<String>,
                         val gradients: Array<Int>,
                         var nativeList: ArrayList<NativeAd>,
                         var itemClick: ClickItem) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ITEM_TYPE = 0
    var AD_TYPE = 1
    var counter = 0
    val AD_COUNT = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE -> CalculatingVH(inflater, parent, object : ClickItem {
                override fun click(position: Int) {
                    itemClick.click(getItemPosition(position))
                }
            })
            AD_TYPE -> AdVH(inflater, parent)
            else -> AdVH(inflater, parent)
        }
    }

    override fun getItemCount(): Int {
        return if (nativeList.isEmpty()) {
            titles.size
        } else {
            titles.size + AD_COUNT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE -> (holder as CalculatingVH).bind(titles[getItemPosition(position)], descriptions[getItemPosition(position)], gradients[getItemPosition(position)])
            AD_TYPE -> (holder as ADVH).bind(nativeList[getAdNumber()])
        }
    }

    private fun getAdNumber(): Int {
        var position = 0
        if (counter > nativeList.size - 1) {
            position = 0
            counter = 1
        } else {
            position = counter
            counter++
        }
        return position
    }

    private fun getItemPosition(position: Int): Int {
        return if (nativeList.isEmpty() || position < 2) {
            position
        } else {
            position - AD_COUNT
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (nativeList.isEmpty() || position < 2) {
            ITEM_TYPE
        } else if (position == 2) {
            AD_TYPE
        } else {
            ITEM_TYPE
        }
    }

    fun insertAds(listAds: ArrayList<NativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }
}