package com.diets.dietplans.presentation.diets.list.old.inside.article

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.diets.dietplans.Config
import com.diets.dietplans.model.ItemOfSubsection
import com.diets.dietplans.model.Subsection
import com.diets.dietplans.R
import com.diets.dietplans.utils.ad.AdWorker
import com.diets.dietplans.utils.analytics.Ampl
import com.diets.dietplans.presentation.diets.list.old.inside.article.controller.PartAdapter
import com.diets.dietplans.utils.PreferenceProvider.isHasPremium
import kotlinx.android.synthetic.main.fr_article.*
import kotlinx.android.synthetic.main.fr_article.appodealBannerView

class ActivityArticle : AppCompatActivity(R.layout.fr_article) {

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AdWorker.checkLoad()
        if (!isHasPremium) {
            appodealBannerView.visibility = View.VISIBLE
            appodealBannerView.loadAd(AdRequest.Builder().build())
        }
        var subsection = intent.getSerializableExtra(Config.ITEM_DATA) as Subsection
        Glide.with(this).load(resources.getStringArray(R.array.images)[subsection.urlOfImage.toInt()]).into(ivCollapsing)
        main_toolbar.title = subsection.description
        Ampl.openDiet(subsection.description)

        rvArticle.layoutManager = LinearLayoutManager(this)
        rvArticle.adapter = PartAdapter(subsection.arrayOfItemOfSubsection as ArrayList<ItemOfSubsection>)

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}