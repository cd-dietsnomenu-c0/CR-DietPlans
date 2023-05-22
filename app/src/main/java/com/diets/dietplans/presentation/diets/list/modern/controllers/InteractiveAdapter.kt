package com.diets.dietplans.presentation.diets.list.modern.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.dietplans.Config
import com.diets.dietplans.model.interactive.AllDiets
import com.diets.dietplans.presentation.diets.list.ItemClick
import com.google.android.gms.ads.nativead.NativeAd

class InteractiveAdapter(val allDiets: AllDiets, var itemClick: ItemClick, var nativeList: ArrayList<NativeAd>, val typeName: String, val isHasHead: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE = 0
    val AD_TYPE = 1
    val HEAD_TYPE = 2
    var counter = 0
    var diff = 0

    init {
        if (isHasHead) {
            diff++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE -> InteractiveVH(inflater, parent, object : ItemClick {
                override fun click(position: Int) {
                    itemClick.click(getRealPosition(position))
                }

                override fun newDietsClick() {
                }
            })
            HEAD_TYPE -> HeadVH(inflater, parent)
            AD_TYPE -> NativeVH(inflater, parent)
            else -> InteractiveVH(inflater, parent, itemClick)
        }

    }

    override fun getItemCount(): Int {
        return if (nativeList.isNotEmpty()) {
            allDiets.dietList.size + allDiets.dietList.size / (Config.WHICH_AD_NEW_DIETS - 1) + diff
        } else {
            return allDiets.dietList.size + diff
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == -1 + diff) {
            HEAD_TYPE
        } else if ((position + 1) % Config.WHICH_AD_NEW_DIETS == 0 && position > 0 && nativeList.isNotEmpty()) {
            AD_TYPE
        } else {
            ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE -> (holder as InteractiveVH).bind(allDiets.dietList[getRealPosition(position)].title,
                    allDiets.dietList[getRealPosition(position)].mainImage, allDiets.dietList[getRealPosition(position)].isNew,
                    allDiets.dietList[getRealPosition(position)].shortIntroduction, allDiets.dietList[getRealPosition(position)].days.size,
                    typeName, allDiets.dietList[getRealPosition(position)].kcal)
            AD_TYPE -> (holder as NativeVH).bind(nativeList[getAdPosition()])
            HEAD_TYPE -> (holder as HeadVH).bind()
        }
    }

    private fun getRealPosition(position: Int): Int {
        return if (nativeList.isEmpty()) {
            position - diff
        } else {
            position - position / Config.WHICH_AD_NEW_DIETS - diff
        }
    }

    fun insertAds(listAds: ArrayList<NativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }

    private fun getAdPosition(): Int {
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
}