package com.diets.dietplans.utils.analytics

import com.diets.dietplans.App
import com.google.firebase.analytics.FirebaseAnalytics

object FBAnalytic {
    fun trial(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("trial", null)
    }

    fun adShow(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("ad_show", null)
    }

    fun adClick(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("ad_click", null)
    }

    fun goToGrade(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("go_to_grade", null)
    }


    fun gradeOneStar(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("grade_one_star", null)
    }

    fun gradeTwoStars(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("grade_two_stars", null)
    }

    fun gradeThreeStars(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("grade_three_stars", null)
    }

    fun gradeFirthStars(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("grade_firth_stars", null)
    }

    fun gradeFifthStars(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("grade_fifth_stars", null)
    }
}