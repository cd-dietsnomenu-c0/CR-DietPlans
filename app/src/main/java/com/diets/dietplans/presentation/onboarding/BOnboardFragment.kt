package com.diets.dietplans.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.diets.dietplans.model.onboard.OnboardUI
import com.diets.dietplans.R
import kotlinx.android.synthetic.main.b_onboard_fragment.*

class BOnboardFragment : Fragment(R.layout.b_onboard_fragment) {

    companion object{
        val TAG_DATA = "AOnboardFragment"

        fun newInstance(onboardUI: OnboardUI) : BOnboardFragment{
            var bundle = Bundle()
            bundle.putSerializable(TAG_DATA, onboardUI)
            var fragment  = BOnboardFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = arguments!!.getSerializable(TAG_DATA) as OnboardUI

        tvTitle.text = data.title
        tvText.text = data.text
        Glide.with(requireContext()).load(data.url).into(ivBack)

        if (!data.isGradientTop){

        }
    }
}