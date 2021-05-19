package com.diets.dietplans.presentation.diets.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.development_dialog.btnClose
import kotlinx.android.synthetic.main.properties_dialog.*

class PropertiesFragment : DialogFragment() {

    companion object{
        val TAG_TITLE = "TAG_TITLE"
        val TAG_TEXT = "TAG_TEXT"
        val TAG_URL = "TAG_URL"

        fun newInstance(title : String, text : String, url : String) : PropertiesFragment{
            var bundle = Bundle()
            bundle.putString(TAG_TITLE, title)
            bundle.putString(TAG_TEXT, text)
            bundle.putString(TAG_URL, url)
            var fragment = PropertiesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.properties_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvTitle.text = arguments!!.getString(TAG_TITLE)
        tvText.text = arguments!!.getString(TAG_TEXT)
        val url = arguments!!.getString(TAG_URL)
        Glide.with(view).load(url).into(ivTitle)
        btnClose.setOnClickListener {
            dismiss()
        }
    }
}