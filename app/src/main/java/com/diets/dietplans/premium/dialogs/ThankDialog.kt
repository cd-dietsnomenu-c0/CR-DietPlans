package com.diets.dietplans.premium.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.thank_dialog.*

class ThankDialog : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.thank_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnEnd.setOnClickListener {
            (targetFragment as Callbacks).close()
            dismiss()
        }
    }



    interface Callbacks {
        fun close()
    }
}