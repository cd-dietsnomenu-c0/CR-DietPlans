package com.diets.dietplans.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.diets.dietplans.App
import com.diets.dietplans.R

object ThankToast {
    fun showThankToast(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_thank_grade, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}