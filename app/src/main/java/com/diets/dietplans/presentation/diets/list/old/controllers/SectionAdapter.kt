package com.diets.dietplans.presentation.diets.list.old.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.diets.dietplans.model.Section
import com.diets.dietplans.presentation.diets.controller.ADVH
import com.diets.dietplans.presentation.diets.list.ItemClick
import com.yandex.mobile.ads.nativeads.NativeAd
import java.util.ArrayList

class SectionAdapter(var sectionList: ArrayList<Section>, var leftDrawables: Array<String>, var itemClick: ItemClick,
                     var nativeList : ArrayList<NativeAd>) : Adapter<RecyclerView.ViewHolder>() {
    val HEAD_TYPE = 0
    val BODY_TYPE = 1
    val AD_TYPE = 2
    val ad_count = 2
    var counter = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            HEAD_TYPE -> HeadVH(inflater, parent, itemClick)
            AD_TYPE -> ADVH(inflater, parent)
            else -> SectionVH(inflater, parent, object : ItemClick {
                override fun click(position: Int) {
                    itemClick.click(getNumber(position))
                }

                override fun newDietsClick() {

                }
            })
        }
    }

    override fun getItemCount(): Int {
        return if (nativeList.isEmpty()) {
            sectionList.size
        }else{
            sectionList.size + ad_count
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
             HEAD_TYPE -> (holder as HeadVH).bind()
             BODY_TYPE -> (holder as SectionVH).bind(sectionList[getNumber(position)], leftDrawables[sectionList[getNumber(position)].urlOfImage.toInt()])
             AD_TYPE -> (holder as ADVH).bind(nativeList[getAdNumber()])
        }
    }

    private fun getAdNumber(): Int {
        var position = 0
        if (counter > nativeList.size - 1){
            position = 0
            counter = 1
        }else{
            position = counter
            counter++
        }
        return position
    }

    private fun getNumber(position: Int): Int {
        return if (nativeList.isEmpty() || position <= 1){
            position
        }else if (position in 3..6){
            position - 1
        }else{
            position - 2
        }
    }

    override fun getItemViewType(position: Int): Int {
        return  if (nativeList.isEmpty() || position <= 1){
            return BODY_TYPE
        }else if (position == 2 || position == 7){
            AD_TYPE
        }else {
            BODY_TYPE
        }
    }

    fun insertAds(listAds: ArrayList<NativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }
}