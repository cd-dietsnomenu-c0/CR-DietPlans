package com.diets.dietplans.common.db

import androidx.room.TypeConverter

class DayLoseConverter {

    @TypeConverter
    fun toBD(listNumbers : MutableList<Int>) : String{
        var string = ""
        for (day in listNumbers.indices){
            string += listNumbers[day].toString()
            if (day != listNumbers.size - 1){
                string += ","
            }
        }
        return string
    }

    @TypeConverter
    fun fromBD(data : String) : MutableList<Int>{
        return if (data == ""){
            mutableListOf()
        }else {
            data.split(",").map { it.toInt() }.toMutableList()
        }
    }
}