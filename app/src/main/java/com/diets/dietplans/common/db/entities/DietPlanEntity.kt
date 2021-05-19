package com.diets.dietplans.common.db.entities

import androidx.room.*
import com.diets.dietplans.model.interactive.Diet
import com.diets.dietplans.common.db.DayLoseConverter

@Entity
@TypeConverters(DayLoseConverter::class)
data class DietPlanEntity(@PrimaryKey var id: Int, var timeTrigger: Long,
                          var name: String, var isCompleted: Boolean,
                          var isNeedNotification: Boolean, var currentDay: Int,
                          var missingDays: Int,
                          var difficulty: Int, var breakfastState: Int,
                          var lunchState: Int, var dinnerState: Int,
                          var snakeState: Int, var snake2State: Int,

                          var numbersLosesDays: MutableList<Int>) {

    constructor(diet: Diet, difficulty: Int, timeInMillis: Long) : this(0, timeInMillis, diet.title, false,
            true, 0, 0, difficulty, 0,
            0, 0, 0, 0, mutableListOf<Int>())
}