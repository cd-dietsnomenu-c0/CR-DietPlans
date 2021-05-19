package com.diets.dietplans.presentation.profile.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.diets.dietplans.R
import com.diets.dietplans.presentation.profile.ProfileFragment
import com.diets.dietplans.utils.PreferenceProvider
import kotlinx.android.synthetic.main.name_dialog.*

class NameDialog: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.name_dialog, container, false)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        edtName.onFocusChangeListener = OnFocusChangeListener { v, hasFocus -> if (hasFocus) edtName.hint = "" else edtName.hint = "Ваше имя или никнейм" }
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnSave.setOnClickListener {
            if (edtName.text.toString() != "") {
                PreferenceProvider.setName(edtName.text.toString())
                (targetFragment as ProfileFragment).bindName()
            }
            dismiss()
        }
    }


}