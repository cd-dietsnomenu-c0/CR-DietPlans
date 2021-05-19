package com.diets.dietplans.common

import com.squareup.moshi.Moshi
import com.diets.dietplans.App
import com.diets.dietplans.model.Global
import com.diets.dietplans.R

object GlobalHolder {

    private lateinit var global : Global

    fun setGlobal(global: Global){
        this.global = global
    }

    fun getGlobal() : Global{
        return if (this::global.isInitialized) {
            global
        }else{
            getAsyncDietData()!!
        }
    }

    private fun getAsyncDietData(): Global? {
        var json: String
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(Global::class.java)
        try {
            var inputStream = App.getContext().assets.open(App.getContext().getString(R.string.adb_path))
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
        }
        return null
    }


}