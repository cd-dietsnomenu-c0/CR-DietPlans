package com.diets.dietplans.utils

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.diets.dietplans.MainActivity
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.alert_grade.*

class GradeAlert : DialogFragment() {

    private lateinit var labels: List<TextView>
    private lateinit var lavs: List<LottieAnimationView>

    private lateinit var increase: Animation
    private lateinit var decrease: Animation

    private lateinit var showLabel: Animation
    private lateinit var hideLabel: Animation

    private lateinit var showClaim: Animation

    private var previous = -1
    private var isProgress = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_grade, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        increase = AnimationUtils.loadAnimation(view.context, R.anim.grade_increase_scale)
        decrease = AnimationUtils.loadAnimation(view.context, R.anim.grade_decrease_scale)
        showLabel = AnimationUtils.loadAnimation(view.context, R.anim.show_rate_label)
        hideLabel = AnimationUtils.loadAnimation(view.context, R.anim.hide_rate_label)
        showClaim = AnimationUtils.loadAnimation(view.context, R.anim.show_claim_edt)
        labels = listOf<TextView>(tvFirst, tvSecond, tvThird, tvFirth, tvFifth)
        lavs = listOf<LottieAnimationView>(lav0, lav1, lav2, lav3, lav4)
        setClickListeners()

        btnLater.setOnClickListener {
            (activity as MainActivity).rateLater()
            dismiss()
        }

        btnStop.setOnClickListener {
            dismiss()
        }

        btnSend.setOnClickListener {
            (activity as MainActivity).sayThank()
            dismiss()
        }
    }

    private fun setClickListeners() {
        for (i in lavs.indices) {
            lavs[i].setOnClickListener {
                if (i != previous && !isProgress) {
                    increase.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            lavs[i].layoutParams.width = dpToPx(45f).toInt()
                            lavs[i].layoutParams.height = dpToPx(45f).toInt()
                            lavs[i].scale = 1.0f
                            lavs[i].clearAnimation()
                            increase.setAnimationListener(null)
                            previous = i
                            isProgress = false
                        }

                        override fun onAnimationStart(animation: Animation?) {
                            isProgress = true
                        }
                    })
                    lavs[i].startAnimation(increase)
                    labels[i].startAnimation(showLabel)
                    labels[i].visibility = View.VISIBLE

                    if (previous != -1) {
                        decrease.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationRepeat(animation: Animation?) {

                            }

                            override fun onAnimationEnd(animation: Animation?) {
                                lavs[previous].layoutParams.width = dpToPx(30f).toInt()
                                lavs[previous].layoutParams.height = dpToPx(30f).toInt()
                                lavs[previous].scale = 0.6f
                                lavs[previous].clearAnimation()
                                decrease.setAnimationListener(null)
                            }

                            override fun onAnimationStart(animation: Animation?) {
                            }
                        })
                        lavs[previous].startAnimation(decrease)
                        labels[previous].startAnimation(hideLabel)
                        labels[previous].visibility = View.INVISIBLE
                    }
                    if (btnSend.visibility != View.VISIBLE) {
                        bindRate(i)
                    }
                }
            }
        }
    }

    private fun bindRate(grade: Int) {
        if (grade > 2 && PreferenceProvider.getRateMind() != PreferenceProvider.RATE_MIND_BAD) {
            moveToMarket()
        } else {
            showSafety()
        }
    }

    private fun showSafety() {
        btnSend.startAnimation(showClaim)
        btnSend.visibility = View.VISIBLE
        PreferenceProvider.setRateMind(PreferenceProvider.RATE_MIND_BAD)
    }

    private fun moveToMarket() {
        object : CountDownTimer(500, 100){
            override fun onFinish() {
                (activity as MainActivity).goToMarket()
                dismiss()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }


    fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }
}