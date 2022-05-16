package com.diets.dietplans.presentation.tracker

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.diets.dietplans.MainActivity
import com.diets.dietplans.R
import com.diets.dietplans.utils.ad.AdWorker
import com.diets.dietplans.utils.ad.NativeSpeaker
import com.diets.dietplans.utils.analytics.Ampl
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder
import kotlinx.android.synthetic.main.load_ad_include.*
import kotlinx.android.synthetic.main.loading_activity.*
import kotlinx.android.synthetic.main.loading_activity.flAdContainer
import java.util.*

class LoadingActivity : AppCompatActivity(R.layout.loading_activity) {

    var lastNumber = -1
    lateinit var animationHide: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Ampl.startLoading()
        AdWorker.observeOnNativeList(object : NativeSpeaker {
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
        Log.e("LOL", "loadNative")
        var nativeAdViewBinder = NativeAdViewBinder
                .Builder(ad_view)
                .setCallToActionView(ad_call_to_action)
                .setIconView(ad_icon)
                .setMediaView(ad_media)
                .setTitleView(ad_headline)
                .setSponsoredView(ad_body)
                .setWarningView(ad_warning)
                .build()

        try {
            nativeAd.bindNativeAd(nativeAdViewBinder)
            flAdContainer.visibility = View.VISIBLE
        } catch (ex: Exception) {
            Ampl.errorNative(ex.toString())
        }
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