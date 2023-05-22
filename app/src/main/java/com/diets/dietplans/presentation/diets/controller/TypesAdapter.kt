package com.diets.dietplans.presentation.diets.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.dietplans.Config
import com.diets.dietplans.presentation.diets.IClick
import com.google.android.gms.ads.nativead.NativeAd

class TypesAdapter(val listSchemas: List<com.diets.dietplans.model.schema.Schema>,
                   var nativeList : ArrayList<NativeAd>,
                   val iClick: IClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val BODY_TYPE = 0
    val AD_TYPE = 1
    var counter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val li = LayoutInflater.from(parent.context)
        return when(viewType){
            BODY_TYPE -> TypesVH(li, parent, object : IClick{
                override fun clickOpen(position: Int) {
                    iClick.clickOpen(getRealPosition(position))
                }

                override fun clickProperties(position: Int) {
                    iClick.clickProperties(getRealPosition(position))
                }
            })
            AD_TYPE -> ADVH(li, parent)
            else -> TypesVH(li, parent, iClick)
        }
    }

    override fun getItemCount(): Int {
        return if(nativeList.isNotEmpty()){
            listSchemas.size + listSchemas.size / (Config.WHICH_AD_NEW_DIETS - 1)
        }else{
            return listSchemas.size
        }
    }

    private fun getRealPosition(position: Int): Int {
        return if (nativeList.isEmpty()){
            position
        }else{
            position - position / Config.WHICH_AD_NEW_DIETS
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            BODY_TYPE -> (holder as TypesVH).bind(listSchemas[getRealPosition(position)])
            AD_TYPE -> (holder as ADVH).bind(nativeList[getAdPosition()])
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

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % Config.WHICH_AD_NEW_DIETS == 0 && position > 0 && nativeList.isNotEmpty()) {
            AD_TYPE
        } else {
            BODY_TYPE
        }
    }
}