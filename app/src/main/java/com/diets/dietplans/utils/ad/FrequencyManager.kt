package com.diets.dietplans.utils.ad

import android.util.Log
import com.diets.dietplans.BuildConfig
import com.diets.dietplans.utils.PreferenceProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FrequencyManager {
    private var hasRequest = false

    fun runSetup() {
        if (!hasRequest) {
            hasRequest = true
            requestPercent()
        }
    }

    private fun requestPercent() {
        var path = "percent_${BuildConfig.VERSION_CODE}"
        FirebaseDatabase.getInstance("https://my-diet-coach.firebaseio.com/")
                .reference
                .child(path)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("LOL", "cancelled ${p0.message}")
                        PreferenceProvider.frequencyPercent = 100
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        Log.e("LOL", "not cancelled")
                        if (p0.getValue(Int::class.java) == null) {
                            createNewDirectory(path)
                        } else {
                            val newPercent = p0.getValue(Int::class.java) ?: 100
                            Log.e("LOL", "$newPercent")
                            PreferenceProvider.frequencyPercent = newPercent
                        }
                    }
                })
    }

    private fun createNewDirectory(path: String) {
        FirebaseDatabase
                .getInstance("https://my-diet-coach.firebaseio.com/")
                .reference
                .child(path)
                .setValue(100)
                .addOnSuccessListener {
                    requestPercent()
                }
    }
}