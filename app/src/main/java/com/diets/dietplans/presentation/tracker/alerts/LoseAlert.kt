package com.diets.dietplans.presentation.tracker.alerts

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.dietplans.R
import com.diets.dietplans.presentation.tracker.FragmentTracker
import kotlinx.android.synthetic.main.alert_lose.*

class LoseAlert : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_lose, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnEnd.setOnClickListener {
            (targetFragment as FragmentTracker).closeDiet()
            dismiss()
        }

        btnReplay.setOnClickListener {
            (targetFragment as FragmentTracker).restartDiet()
            dismiss()
        }
    }
}