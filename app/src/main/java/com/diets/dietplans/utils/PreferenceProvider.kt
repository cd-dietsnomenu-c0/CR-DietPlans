package com.diets.dietplans.utils

import android.content.Context
import android.content.SharedPreferences
import com.diets.dietplans.App

object PreferenceProvider {

    private const val RATE_MIND = "RATE_MIND"
    const val RATE_MIND_GOOD = "RATE_MIND_GOOD"
    const val RATE_MIND_BAD = "RATE_MIND_BAD"
    const val FIRST_TIME = "FIRST_TIME"
    const val USER_NAME = "USER_NAME"
    const val WALLPAPER_NUMBER = "WALLPAPER_NUMBER"
    const val COUNT_INTRO = "COUNT_INTRO"
    const val PHOTO_URI = "PHOTO_URI"
    const val EMPTY_PHOTO = "EMPTY_PHOTO"
    const val LAST_ENTER = "FIRST_ENTER"
    const val EMPTY_LAST_ENTER = -1L
    const val FIRST_REACT = "FIRST_REACT"
    const val SECOND_REACT = "SECOND_REACT"
    const val VERSION = "VERSION"


    private fun getInstance(): SharedPreferences? {
        val sp = App.getInstance().getSharedPreferences(
                App.getInstance().packageName + ".SharedPreferences",
                Context.MODE_PRIVATE
        )
        return sp
    }

    private fun editor(put: (SharedPreferences.Editor?) -> SharedPreferences.Editor?) = put(getInstance()?.edit())?.apply()

    fun setRateMind(mind: String) = editor { it?.putString(RATE_MIND, mind) }
    fun getRateMind() = getInstance()?.getString(RATE_MIND, "")

    fun setFirstTime(time: String) = editor { it?.putString(FIRST_TIME, time) }
    fun getFirstTime() = getInstance()?.getString(FIRST_TIME, "")

    fun setName(name: String) = editor { it?.putString(USER_NAME, name) }
    fun getName() = getInstance()?.getString(USER_NAME, "")

    fun setBack(position: Int) = editor { it?.putInt(WALLPAPER_NUMBER, position) }
    fun getBack() = getInstance()?.getInt(WALLPAPER_NUMBER, 0)

    fun setCountIntro(count: Int) = editor { it?.putInt(COUNT_INTRO, count) }
    fun getCountIntro() = getInstance()?.getInt(COUNT_INTRO, 0)

    fun setPhoto(uri: String) = editor { it?.putString(PHOTO_URI, uri) }
    fun getPhoto() = getInstance()?.getString(PHOTO_URI, EMPTY_PHOTO)

    fun setLastEnter(time: Long) = editor { it?.putLong(LAST_ENTER, time) }
    fun getLastEnter() = getInstance()?.getLong(LAST_ENTER, EMPTY_LAST_ENTER)

    fun setFirstShow() = editor { it?.putBoolean(FIRST_REACT, true) }
    fun getFirstShow() = getInstance()?.getBoolean(FIRST_REACT, false)

    fun setSecondShow() = editor { it?.putBoolean(SECOND_REACT, true) }
    fun getSecondShow() = getInstance()?.getBoolean(SECOND_REACT, false)

    fun setVersion(version: String) = editor { it?.putString(VERSION, version) }
    fun getVersion() = getInstance()?.getString(VERSION, ABConfig.A)

    private const val LANG_TAG = "LANG_TAG"
    const val DEFAULT_LOCALE = "DEFAULT_LOCALE"

    var locale: String
        get() = getInstance()?.getString(LANG_TAG, DEFAULT_LOCALE)!!
        set(value) = editor { it?.putString(LANG_TAG, value) }!!

    private const val LANG_WARNING_TAG = "LANG_WARNING_TAG"

    var isShowLangWarning: Boolean
        get() = getInstance()?.getBoolean(LANG_WARNING_TAG, false)!!
        set(value) = editor { it?.putBoolean(LANG_WARNING_TAG, value) }!!

    private const val AD_PERCENT_TAG = "AD_PERCENT_TAG"
    var frequencyPercent: Int
        get() = getInstance()?.getInt(AD_PERCENT_TAG, 0)!!
        set(value) = editor { it?.putInt(AD_PERCENT_TAG, value) }!!
}