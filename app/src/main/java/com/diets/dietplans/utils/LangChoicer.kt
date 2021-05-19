package com.diets.dietplans.utils

object LangChoicer {

    const val RU = "ru"
    const val EN = "en"

    fun getNumber(localeCode: String): Int {
        return when (localeCode) {
            PreferenceProvider.DEFAULT_LOCALE -> 0
            RU -> 1
            EN -> 2
            else -> 0
        }
    }

    fun getLocaleCode(numberLocale: Int): String {
        return when (numberLocale) {
            0 -> PreferenceProvider.DEFAULT_LOCALE
            1 -> RU
            2 -> EN
            else -> PreferenceProvider.DEFAULT_LOCALE
        }
    }
}