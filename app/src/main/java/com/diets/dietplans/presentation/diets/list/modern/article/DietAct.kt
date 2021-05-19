package com.diets.dietplans.presentation.diets.list.modern.article

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.dietplans.App
import com.diets.dietplans.Config
import com.diets.dietplans.model.interactive.*
import com.diets.dietplans.R
import com.diets.dietplans.utils.ad.AdWorker
import com.diets.dietplans.utils.ad.NativeSpeaker
import com.diets.dietplans.utils.analytics.Ampl
import com.diets.dietplans.common.DBHolder
import com.diets.dietplans.common.db.entities.DietPlanEntity
import com.diets.dietplans.common.db.entities.FavoriteEntity
import com.diets.dietplans.presentation.diets.list.modern.article.dialogs.DifficultyFragment
import com.diets.dietplans.presentation.diets.list.modern.article.dialogs.RewriteAlert
import com.diets.dietplans.presentation.diets.list.modern.article.controller.DietAdapter
import com.diets.dietplans.presentation.diets.list.modern.article.controller.IContents
import com.diets.dietplans.presentation.diets.list.modern.article.controller.managers.LayoutManagerTopScroll
import com.diets.dietplans.presentation.diets.list.modern.article.toasts.AddToast
import com.diets.dietplans.presentation.tracker.LoadingActivity
import kotlinx.android.synthetic.main.diet_act.*
import java.util.*

class DietAct : AppCompatActivity(R.layout.diet_act) {

    lateinit var diet: Diet
    private var TAG = "ALERT_DIET_ACT"
    var isHided = false
    var isShowed = true
    var isNeedShowConnect = false
    lateinit var showAnim : Animation
    lateinit var hideAnim : Animation

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showAnim = AnimationUtils.loadAnimation(this, R.anim.show_start_button)
        hideAnim = AnimationUtils.loadAnimation(this, R.anim.hide_start_button)

        AdWorker.checkLoad()
        diet = intent.getSerializableExtra(Config.NEW_DIET) as Diet
        isNeedShowConnect = intent.getSerializableExtra(Config.NEED_SHOW_CONNECT) as Boolean

        lavLike.speed = 1.5f
        lavLike.setMinFrame(45)
        lavLike.setMaxFrame(100)
        bindFavoriteState()

        rvDiet.layoutManager = LayoutManagerTopScroll(this)
        var adapter = DietAdapter(diet, object : IContents {
            override fun moveTo(position: Int) {
                rvDiet.smoothScrollToPosition(position)
            }
        }, arrayListOf())
        rvDiet.adapter = adapter
        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<UnifiedNativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
        if (isNeedShowConnect) {
            rvDiet.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 5) {
                        hideStartButton()
                    } else if (dy < -5){
                        showScrollButton()
                    }
                }
            })
        }else{
            flBottom.visibility = View.INVISIBLE
        }
        Ampl.openNewDiet(diet.title)

        btnStart.setOnClickListener {
            if (DBHolder.get().name != DBHolder.NO_DIET_YET) {
                RewriteAlert().show(supportFragmentManager, TAG)
            }else{
                showNewDietAlert()
            }
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindFavoriteState() {
        if (App.getInstance().db.dietDAO().getCurrentFavorite(diet.index).isNotEmpty()){
            setFavoriteState()
        }else{
            setNoFavoriteState()
        }
    }

    private fun setFavoriteState(){
        lavLike.frame = 160
        lavLike.setOnClickListener {
            lavLike.frame = 45
            App.getInstance().db.dietDAO().deleteFavorite(diet.index)
            setNoFavoriteState()
        }
    }

    private fun setNoFavoriteState(){
        lavLike.setOnClickListener {
            if (!lavLike.isAnimating) {
                lavLike.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        setFavoriteState()
                        lavLike.removeAllAnimatorListeners()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
                lavLike.playAnimation()
                App.getInstance().db.dietDAO().addFavorite(FavoriteEntity(diet.index))
                AddToast.showThankToast(this@DietAct)
            }
        }
    }

    private fun showScrollButton() {
        if (!isShowed){
            isHided = false
            isShowed = true
            flBottom.startAnimation(showAnim)
            flBottom.visibility = View.VISIBLE
        }
    }

    private fun hideStartButton() {
        if (!isHided){
            isShowed = false
            isHided = true
            flBottom.startAnimation(hideAnim)
            flBottom.visibility = View.INVISIBLE
        }
    }

    fun showNewDietAlert(){
        DifficultyFragment().show(supportFragmentManager, TAG)
    }

    fun startDietPlan(difficulty: Int) {
        Ampl.choiseHardLevel()
        var entity = DietPlanEntity(diet, difficulty, DBHolder.getTomorrowTimeTrigger())
        DBHolder.firstSet(entity, diet.days)
        startActivity(Intent(this, LoadingActivity::class.java))
        finishAffinity()
    }

}