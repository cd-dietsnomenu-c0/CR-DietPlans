package com.diets.dietplans.presentation.diets

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.dietplans.Config
import com.diets.dietplans.model.Global
import com.diets.dietplans.model.interactive.AllDiets
import com.diets.dietplans.model.interactive.Diet
import com.diets.dietplans.model.schema.Schema
import com.diets.dietplans.R
import com.diets.dietplans.utils.ad.AdWorker
import com.diets.dietplans.utils.ad.NativeSpeaker
import com.diets.dietplans.common.GlobalHolder
import com.diets.dietplans.presentation.diets.controller.TypesAdapter
import com.diets.dietplans.presentation.diets.dialogs.PropertiesFragment
import com.diets.dietplans.presentation.diets.list.modern.NewDietsListActivity
import com.diets.dietplans.presentation.diets.list.old.OldDietsActivity
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.fr_types.*

class FragmentTypes : Fragment(R.layout.fr_types) {

    lateinit var adapter : TypesAdapter
    lateinit var global: Global

    companion object{
        var KEY = "FragmentTypes"
        fun newInstance(global: Global) : FragmentTypes{
            var bundle = Bundle()
            bundle.putSerializable(KEY, global)
            var frag = FragmentTypes()
            frag.arguments = bundle
            return frag
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        global = GlobalHolder.getGlobal()
        adapter = TypesAdapter(global.schemas, arrayListOf(), object : IClick{
            override fun clickOpen(position: Int) {
                openList(position)
            }

            override fun clickProperties(position: Int) {
                PropertiesFragment.newInstance(global.schemas[position].title, global.schemas[position].prop, global.schemas[position].headImage).show(childFragmentManager, "")
            }
        })
        rvTypes.layoutManager = LinearLayoutManager(view.context)
        rvTypes.adapter = adapter
        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                adapter.insertAds(nativeList)
            }
        })


    }

    private fun openList(position: Int) {
        if (global.schemas[position].isOld){
            startActivity(Intent(activity, OldDietsActivity::class.java)
                    .putExtra(Config.OLD_DIETS_GLOBAL, getSections(global)))
        }else{
            startActivity(Intent(activity, NewDietsListActivity::class.java)
                    .putExtra(Config.NEW_DIETS, getNewDiets(global.schemas[position]))
                    .putExtra(Config.TYPE_NAME, global.schemas[position].title)
                    .putExtra(Config.HEADER_TAG, global.schemas[position].plan))
        }
    }

    private fun getSections(global: Global): Global {
        return Global(global.sectionsArray, null, null, null)
    }

    private fun getNewDiets(schema: Schema): AllDiets {
        var reversed = global.allDiets.dietList.reversed()
        var listDiets = mutableListOf<Diet>()
        for (i in schema.reverseDietIndexes.indices){
            listDiets.add(reversed[schema.reverseDietIndexes[i]])
        }
        return AllDiets(schema.title, listDiets.toList())
    }
}