package com.diets.dietplans.presentation.tracker

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.dietplans.MainActivity
import com.diets.dietplans.R
import com.diets.dietplans.utils.ad.AdWorker
import com.diets.dietplans.utils.ad.NativeSpeaker
import com.diets.dietplans.utils.analytics.Ampl
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.load_ad_include.*
import kotlinx.android.synthetic.main.loading_activity.*

class LoadingActivity : AppCompatActivity(R.layout.loading_activity) {

    var lastNumber = -1
    lateinit var animationHide: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Ampl.startLoading()
        AdWorker.observeOnNativeList(object : NativeSpeaker{
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                if (nativeList.size > 0) {
                    loadNative(nativeList[0])
                }
            }
        })
        animationHide = AnimationUtils.loadAnimation(this, R.anim.anim_hide_loading)
        lavLoading.loop(false)
        lavLoadingComplete.loop(false)
        lavLoading.setAnimation("tracker_loading.json")
        lavLoading.speed = 0.8f
        lavLoadingComplete.setAnimation("tracker_loading_complete.json")
        lavLoading.playAnimation()

        animationHide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                tvStateLoading.text = getString(R.string.ready)
                lavLoadingComplete.playAnimation()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        lavLoading.addAnimatorUpdateListener {
            changeState(it.animatedValue as Float)
        }


        lavLoading.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                lavLoading.startAnimation(animationHide)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })

        lavLoadingComplete.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                startCountDown()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }

    private fun loadNative(nativeAd: NativeAd) {
        ad_view.mediaView = ad_media
        ad_view.headlineView = ad_headline
        ad_view.bodyView = ad_body
        ad_view.callToActionView = ad_call_to_action
        ad_view.iconView = ad_icon
        (ad_view.headlineView as TextView).text = nativeAd.headline
        (ad_view.bodyView as TextView).text = nativeAd.body
        (ad_view.callToActionView as Button).text = nativeAd.callToAction
        val icon = nativeAd.icon
        if (icon == null) {
            ad_view.iconView!!.visibility = View.INVISIBLE
        } else {
            (ad_view.iconView as ImageView).setImageDrawable(icon.drawable)
            ad_view.iconView!!.visibility = View.VISIBLE
        }
        ad_view.setNativeAd(nativeAd)
        flAdContainer.visibility = View.VISIBLE
    }


    private fun startCountDown() {
        object : CountDownTimer(500, 500) {
            override fun onFinish() {
                Ampl.setTrackerStatus()
                Ampl.endLoading()
                startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    private fun changeState(value: Float) {
        var progress = (value * 100).toInt()
        if (progress % 20 == 0 && progress != lastNumber) {
            lastNumber = progress
            tvStateLoading.text = resources.getStringArray(R.array.loading_states)[progress / 20]
        }
    }

}