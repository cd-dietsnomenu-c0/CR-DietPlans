package com.diets.dietplans.presentation.diets.list.modern.article.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.dietplans.R
import com.diets.dietplans.presentation.diets.list.modern.article.DietAct
import kotlinx.android.synthetic.main.rewrite_alert.*

class RewriteAlert : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.rewrite_alert, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnOk.setOnClickListener {
            (activity as DietAct).showNewDietAlert()
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}