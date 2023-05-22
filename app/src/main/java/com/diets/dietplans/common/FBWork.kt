package com.diets.dietplans.common

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.diets.dietplans.model.Global
import com.diets.dietplans.model.interactive.*
import com.diets.dietplans.model.schema.Schema
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class FBWork {
    companion object {
        private fun addNewDiets(global: Global) {
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.getReference("empty")
            val fd = FirebaseDatabase.getInstance()
            val dr = fd.getReference("newDiets12")
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.getValue(AllDiets::class.java)!!.dietList.indices) {
                        //global.allDiets.dietList.add(dataSnapshot.getValue(AllDiets::class.java)!!.dietList[i])
                    }
                    dr.setValue(global)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }


        fun getFCMToken() {
            FirebaseMessaging.getInstance().token
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w("LOL", "getInstanceId failed", task.exception)
                            return@OnCompleteListener
                        }
                        Log.e("LOL", "FCM token -- ${task.result.toString()}")
                    })
        }

        private fun swapList(global: Global) {
            var list: List<Diet> = global.allDiets.dietList
            Collections.reverse(list)
            //global.allDiets.dietList = list
            val fd = FirebaseDatabase.getInstance()
            val dr = fd.getReference("swapPlace")
            dr.setValue(global)
        }

        fun clearList(global: Global) {
            for (i in global.allDiets.dietList.indices) {
                for (j in global.allDiets.dietList[i].days.indices) {
                    var k = 0
                    while (k < global.allDiets.dietList[i].days[j].eats.size) {
                        if (global.allDiets.dietList[i].days[j].eats[k].text == "") {
                            global.allDiets.dietList[i].days[j].eats.drop(k)
                            k--
                        }
                        k++
                    }
                }
            }
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.getReference("clearAdb")
            databaseReference.setValue(global)
        }

        fun setNewLabels(count: Int, global: Global) {
            for (i in 0..count){
                global.allDiets.dietList[i].isNew = true
            }
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.getReference("labels")
            databaseReference.setValue(global)
        }

        fun falseLoadEmpty() {
            val review = Review("",
                    "",
                    "")


            val eat = Eat(0, "")
            val eat1 = Eat(1, "")
            val eat2 = Eat(2, "")
            val eat3 = Eat(3, "")
            val eat4 = Eat(4, "")

            var eatList = ArrayList<Eat>()
            eatList.add(eat)
            eatList.add(eat1)
            eatList.add(eat4)
            eatList.add(eat2)
            eatList.add(eat3)

            val day = DietDay("", "", eatList)
            var days = ArrayList<DietDay>()
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)

            var benList = ArrayList<String>()
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")

            var consList = ArrayList<String>()
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")

            var diet = Diet("", 0, "", "", "", "", benList, "", consList, "", "", days, "", "", review, false, 0)

            var listDiets = ArrayList<Diet>()
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)

            var allDiets = AllDiets("", listDiets)

            FirebaseDatabase.getInstance().getReference("empty").setValue(allDiets)
        }

        fun setSchema(global: Global){
            var listSchemas = listOf<Schema>(Schema("", "Низкоуглеводные диеты", "", 1, true, true, listOf(0, 4, 5, 7, 10, 15, 16, 18, 20, 21, 22, 24, 29), false, -1, false),
                    Schema("", "Монодиеты", "", 1, true, true,listOf(1, 6, 8, 9, 13, 26, 27, 28), false, -1, false),
                    Schema("", "Экстремальные диеты","", 2, true, true, listOf(2, 12, 17, 19, 23, 25), false, -1, false),
                    Schema("", "Лечебные диеты","", 0, true, true, listOf(14), false, -1, false),
                    Schema("", "Сбалансированные диеты","", 0, true, true, listOf(3, 11), false, -1, false),
                    Schema("", "Старые диеты","", 1, false, false, listOf(), true, 24, false))

            global.schemas = listSchemas
            FirebaseDatabase.getInstance().getReference("withSchema").setValue(global)
        }
    }


}