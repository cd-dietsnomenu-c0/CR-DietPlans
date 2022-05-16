package com.diets.dietplans.presentation.diets.list.old.inside.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.Config
import com.diets.dietplans.model.Subsection
import com.diets.dietplans.presentation.diets.controller.ADVH
import com.diets.dietplans.presentation.diets.list.modern.controllers.NativeVH
import com.diets.dietplans.presentation.diets.list.ItemClick
import com.yandex.mobile.ads.nativeads.NativeAd
import java.util.*

class ItemAdapter(val list: ArrayList<Subsection>,
                  var drawables: Array<String>,
                  var itemClick: ItemClick,
                  var nativeList : ArrayList<NativeAd>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_TYPE = 0
    val AD_TYPE = 1
    var counter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            ITEM_TYPE -> ItemVH(inflater, parent, object : ItemClick {
                override fun click(position: Int) {
                    itemClick.click(getRealPosition(position))
                }

                override fun newDietsClick() {
                }
            })
            AD_TYPE -> ADVH(inflater, parent)
            else -> ItemVH(inflater, parent, itemClick)
        }
    }

    private fun getRealPosition(position: Int): Int {
        return if (nativeList.isEmpty()){
            position
        }else{
            position - position / Config.WHICH_AD_NEW_DIETS
        }
    }

    override fun getItemCount(): Int {
        return if(nativeList.isNotEmpty()){
            list.size + list.size / (Config.WHICH_AD_NEW_DIETS - 1)
        }else{
            return list.size
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
            ITEM_TYPE ->(holder as ItemVH).bind(list[getRealPosition(position)], drawables[list[getRealPosition(position)].urlOfImage.toInt()])
            AD_TYPE ->(holder as ADVH).bind(nativeList[getAdPosition()])
        }
    }

    fun insertAds(listAds: ArrayList<NativeAd>) {
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