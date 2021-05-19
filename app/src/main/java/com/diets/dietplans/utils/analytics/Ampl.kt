package com.diets.dietplans.utils.analytics

import com.amplitude.api.Amplitude
import com.amplitude.api.Identify
import org.json.JSONException
import org.json.JSONObject

class Ampl {
    companion object {
        val show_ad = "show_ad"
        val failed_all_loads = "failed_all_loads"
        val failed_one_loads = "failed_one_loads"
        val run_app = "run"
        val show_diet_section = "show_diet_section"
        val show_new_diet_section = "show_new_diet_section"
        val show_calculation_section = "show_calculation_section"
        val show_settings_section = "show_settings_section"

        //Open diets
        val open_subsection_diet = "open_subsection_diet"
        val open_subsection_diet_name = "name"

        val open_diet = "open_diet"
        val open_new_diet = "open_new_diet"
        val open_diet_name = "name_of_diet"


        //Open calculations
        val open_calculator = "open_calculator"
        val open_calculator_name = "name_of_calculator"

        val use_calculator = "use_calculator"
        val use_calculator_name = "use_calculator_name"

        //Settings
        val settings_pp = "settings_pp"
        val settings_grade = "settings_grade"
        val settings_share = "settings_share"

        val recieve_fcm = "recieve_fcm"
        val open_from_push = "open_from_push"

        val show_hard_card = "show_hard_card"
        val choise_hard_level = "choise_hard_level"
        val start_loading = "start_loading"
        val end_loading = "end_loading"
        val open_tracker = "open_tracker"

        val TRACKER_STATUS = "TRACKER_STATUS"
        val tracker_use = "tracker_use"

        val second_notify = "second_notify"
        val first_notify = "first_notify"
        val open_profile = "open_profile"
        val click_avatar = "click_avatar"
        val open_favorites = "open_favorites"
        val open_trophy = "open_trophy"
        val open_wallpapers = "open_wallpapers"
        val send_claim = "send_claim"

        val set_ver = "set_ver"
        val AB = "AB"


        fun setVersion() {
            Amplitude.getInstance().logEvent(set_ver)
        }

        fun setABVersion(version : String){
            var identify = Identify().setOnce(AB, version)
            Amplitude.getInstance().identify(identify)
        }

        fun setTrackerStatus(){
            var identify = Identify().set(TRACKER_STATUS, tracker_use)
            Amplitude.getInstance().identify(identify)
        }

        fun sendClaim() {
            Amplitude.getInstance().logEvent(send_claim)
        }

        fun openWallpapers() {
            Amplitude.getInstance().logEvent(open_wallpapers)
        }

        fun openTrophy() {
            Amplitude.getInstance().logEvent(open_trophy)
        }

        fun openFavorites() {
            Amplitude.getInstance().logEvent(open_favorites)
        }

        fun clickAvatar() {
            Amplitude.getInstance().logEvent(click_avatar)
        }

        fun openProfile() {
            Amplitude.getInstance().logEvent(open_profile)
        }

        fun showFirstNotify() {
            Amplitude.getInstance().logEvent(first_notify)
        }

        fun showSecondNotify() {
            Amplitude.getInstance().logEvent(second_notify)
        }


        fun showHardCard() {
            Amplitude.getInstance().logEvent(show_hard_card)
        }

        fun choiseHardLevel() {
            Amplitude.getInstance().logEvent(choise_hard_level)
        }

        fun startLoading() {
            Amplitude.getInstance().logEvent(start_loading)
        }

        fun endLoading() {
            Amplitude.getInstance().logEvent(end_loading)
        }

        fun openTracker() {
            Amplitude.getInstance().logEvent(open_tracker)
        }

        fun recieveFCM() {
            Amplitude.getInstance().logEvent(recieve_fcm)
        }

        fun openFromPush() {
            Amplitude.getInstance().logEvent(open_from_push)
        }

        fun failedAllLoads() {
            Amplitude.getInstance().logEvent(failed_all_loads)
        }

        fun failedOneLoads() {
            Amplitude.getInstance().logEvent(failed_one_loads)
        }

        fun run() {
            Amplitude.getInstance().logEvent(run_app)
        }

        fun showAd() {
            Amplitude.getInstance().logEvent(show_ad)
        }

        fun openNewDiets() {
            Amplitude.getInstance().logEvent(show_new_diet_section)
        }

        fun openDiets() {
            Amplitude.getInstance().logEvent(show_diet_section)
        }

        fun openCalculators() {
            Amplitude.getInstance().logEvent(show_calculation_section)
        }

        fun openSettings() {
            Amplitude.getInstance().logEvent(show_settings_section)
        }

        fun openSettingsPP() {
            Amplitude.getInstance().logEvent(settings_pp)
        }

        fun openSettingsGrade() {
            Amplitude.getInstance().logEvent(settings_grade)
        }

        fun openSettingsShare() {
            Amplitude.getInstance().logEvent(settings_share)
        }

        fun openSubSectionDiets(nameSection: String) {
            val eventProperties = JSONObject()
            try {
                eventProperties.put(open_subsection_diet_name, nameSection)
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            Amplitude.getInstance().logEvent(open_subsection_diet, eventProperties)
        }

        fun openNewDiet(nameDiet: String) {
            val eventProperties = JSONObject()
            try {
                eventProperties.put(open_diet_name, nameDiet)
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            Amplitude.getInstance().logEvent(open_new_diet, eventProperties)
        }

        fun openDiet(nameDiet: String) {
            val eventProperties = JSONObject()
            try {
                eventProperties.put(open_diet_name, nameDiet)
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            Amplitude.getInstance().logEvent(open_diet, eventProperties)
        }

        fun openCalcualtor(name: String) {
            val eventProperties = JSONObject()
            try {
                eventProperties.put(open_calculator_name, name)
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            Amplitude.getInstance().logEvent(open_calculator, eventProperties)
        }

        fun useCalcualtor(name: String) {
            val eventProperties = JSONObject()
            try {
                eventProperties.put(use_calculator_name, name)
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            Amplitude.getInstance().logEvent(use_calculator, eventProperties)
        }

    }
}