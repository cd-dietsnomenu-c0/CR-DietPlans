package com.diets.dietplans.presentation.profile.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.dietplans.App
import com.diets.dietplans.Config
import com.diets.dietplans.model.interactive.Diet
import com.diets.dietplans.R
import com.diets.dietplans.common.GlobalHolder
import com.diets.dietplans.presentation.diets.list.ItemClick
import com.diets.dietplans.presentation.diets.list.modern.article.DietAct
import com.diets.dietplans.presentation.profile.favorites.controllers.FavoritesAdapter
import kotlinx.android.synthetic.main.favorites_activity.*

class FavoritesActivity : AppCompatActivity(R.layout.favorites_activity) {

    lateinit var adapter: FavoritesAdapter
    lateinit var dietList : MutableList<Diet>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        setUIState()
    }



    private fun setUIState() {
        if (App.getInstance().db.dietDAO().getAllFavorites().isEmpty()){
            showEmptyState()
        }else{
            showFilledState()
        }
    }

    private fun showFilledState() {
        lavEmpty.visibility = View.INVISIBLE
        tvEmptyTitle.visibility = View.INVISIBLE
        tvEmptyText.visibility = View.INVISIBLE
        rvFavorites.visibility = View.VISIBLE
        fillList()
    }

    private fun fillList() {
        adapter = FavoritesAdapter(getFavorites(), object : ItemClick{
            override fun click(position: Int) {
                startActivity(Intent(this@FavoritesActivity, DietAct::class.java)
                        .putExtra(Config.NEW_DIET, dietList[position])
                        .putExtra(Config.NEED_SHOW_CONNECT, true))
            }

            override fun newDietsClick() {
            }
        }, arrayListOf())
        rvFavorites.layoutManager = LinearLayoutManager(this)
        rvFavorites.adapter = adapter
    }

    private fun getFavorites(): MutableList<Diet> {
        var list = mutableListOf<Diet>()
        val dbList = App.getInstance().db.dietDAO().getAllFavorites()
        for (i in dbList.indices){
            for (j in GlobalHolder.getGlobal().allDiets.dietList.indices){
                if (GlobalHolder.getGlobal().allDiets.dietList[j].index == dbList[i].id){
                    list.add(GlobalHolder.getGlobal().allDiets.dietList[j])
                }
            }
        }
        dietList = list
        return list
    }

    private fun showEmptyState() {
        lavEmpty.visibility = View.VISIBLE
        tvEmptyTitle.visibility = View.VISIBLE
        tvEmptyText.visibility = View.VISIBLE
        rvFavorites.visibility = View.INVISIBLE
    }


}