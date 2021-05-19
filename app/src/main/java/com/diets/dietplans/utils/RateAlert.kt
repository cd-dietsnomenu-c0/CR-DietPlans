package com.diets.dietplans.utils

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.alert_rate.*

class RateAlert : DialogFragment() {

    private lateinit var lavs : List<LottieAnimationView>
    private lateinit var timer : CountDownTimer
    private var counter = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_rate, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lavs = listOf<LottieAnimationView>(lav0, lav1, lav2, lav3, lav4)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        for (i in lavs.indices){
            lavs[i].setOnClickListener {
                for (j in lavs.indices){
                    lavs[j].progress = 0.0f
                }
                timer = object : CountDownTimer(1000L * (i + 1), 1000){
                    override fun onFinish() {
                        counter = 0
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        lavs[counter].playAnimation()
                        counter++
                    }
                }
                timer.start()
            }
        }
    }
}