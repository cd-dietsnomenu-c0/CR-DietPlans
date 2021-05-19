package com.diets.dietplans.presentation.profile.favorites.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.dietplans.Config
import com.diets.dietplans.model.interactive.Diet
import com.diets.dietplans.presentation.diets.list.ItemClick
import com.diets.dietplans.presentation.diets.list.modern.controllers.InteractiveVH
import com.diets.dietplans.presentation.diets.list.modern.controllers.NativeVH

class FavoritesAdapter(val allDiets: MutableList<Diet>, var itemClick: ItemClick, var nativeList : ArrayList<UnifiedNativeAd>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE = 0
    val AD_TYPE = 1
    var counter = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            ITEM_TYPE -> InteractiveVH(inflater, parent, object : ItemClick {
                override fun click(position: Int) {
                    itemClick.click(getRealPosition(position))
                }

                override fun newDietsClick() {
                }
            })
            AD_TYPE -> NativeVH(inflater, parent)
            else -> InteractiveVH(inflater, parent, itemClick)
        }

    }

    override fun getItemCount(): Int {
        return if(nativeList.isNotEmpty()){
            allDiets.size  + allDiets.size / (Config.WHICH_AD_NEW_DIETS - 1)
        }else{
            return allDiets.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % Config.WHICH_AD_NEW_DIETS == 0 && position > 0 && nativeList.isNotEmpty()) {
            AD_TYPE
        } else {
            ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ITEM_TYPE ->(holder as InteractiveVH).bind(allDiets[getRealPosition(position)].title,
                    allDiets[getRealPosition(position)].mainImage, allDiets[getRealPosition(position)].isNew, allDiets[getRealPosition(position)].shortIntroduction, allDiets[getRealPosition(position)].days.size, "Фавориты", allDiets[getRealPosition(position)].kcal)
            AD_TYPE ->(holder as NativeVH).bind(nativeList[getAdPosition()])
        }
    }

    private fun getRealPosition(position: Int): Int {
        return if (nativeList.isEmpty()){
            position
        }else{
            position - position / Config.WHICH_AD_NEW_DIETS
        }
    }

    fun insertAds(listAds: ArrayList<UnifiedNativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }

    private fun getAdPosition() : Int{
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
}