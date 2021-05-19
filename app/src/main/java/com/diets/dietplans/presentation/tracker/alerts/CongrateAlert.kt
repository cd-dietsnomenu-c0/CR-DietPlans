package com.diets.dietplans.presentation.tracker.alerts

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.dietplans.R
import com.diets.dietplans.presentation.tracker.FragmentTracker
import kotlinx.android.synthetic.main.alert_congrate.*

class CongrateAlert : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_congrate, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        isCancelable = false
        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fabEnd.setOnClickListener {
            (targetFragment as FragmentTracker).closeDiet()
            dismiss()
        }

        fabShare.setOnClickListener {
            (targetFragment as FragmentTracker).share()
        }
    }
}