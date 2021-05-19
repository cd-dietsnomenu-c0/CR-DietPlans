package com.diets.dietplans.common

import android.util.Log
import com.diets.dietplans.App
import com.diets.dietplans.model.interactive.DietDay
import com.diets.dietplans.common.db.entities.DietPlanEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

object DBHolder {
    var NOT_USE = -1
    var CHECKED = 1
    var NOT_CHECKED = 0
    var ONE_DAY = 86400000

    var DIET_LOSE = -1
    var DIET_CONTINUE = 0
    var DIET_COMPLETED = 1

    var NO_DIET_YET = "NO_DIET_YET"
    var INIT_DIET = "INIT_DIET"

    private var dietPlanEntity: DietPlanEntity

    init {
        dietPlanEntity = DietPlanEntity(0, 0, INIT_DIET,
                false, false, 0, 0,
                0, 0, 0, 0, 0, 0, mutableListOf())
    }

    fun set(dietPlanEntity: DietPlanEntity) {
        this.dietPlanEntity = dietPlanEntity
    }

    fun get(): DietPlanEntity {
        if (dietPlanEntity.name == INIT_DIET) {
            dietPlanEntity = App.getInstance().db.dietDAO().getAll()[0]
        }
        return dietPlanEntity
    }

    fun getIfExist(): DietPlanEntity {
        return dietPlanEntity
    }

    fun delete() {
        clearDB()
        setEmpty()
    }

    private fun clearDB() {
        Single.fromCallable {
            App.getInstance().db.dietDAO().clearDiet(this.dietPlanEntity)
            null
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> Log.e("LOL", "saved") }) { obj: Throwable -> obj.printStackTrace() }
    }


    fun firstSet(dietPlanEntity: DietPlanEntity, days: List<DietDay>) {
        this.dietPlanEntity = dietPlanEntity
        setMeals(days)
        insertInDB()
    }

    fun bindNewDay(days: List<DietDay>): Int {
        checkLastDay()
        var isLastDayHandled = checkNotOpenedDays(days)
        if (dietPlanEntity.currentDay + 1 < days.size) {
            dietPlanEntity.currentDay += 1
            dietPlanEntity.timeTrigger += ONE_DAY
        }
        if (dietPlanEntity.missingDays > dietPlanEntity.difficulty) {
            return DIET_LOSE
        } else if (isDietNotCompleted(days) && !isLastDayHandled) {
            setMeals(days)
            insertInDB()
            return DIET_CONTINUE
        } else {
            return DIET_COMPLETED
        }
    }

    private fun isDietNotCompleted(days: List<DietDay>): Boolean {
        return days.size >= dietPlanEntity.currentDay + 1
    }

    private fun checkLastDay() {
        if (!isCompletedYesterday() && dietPlanEntity.currentDay !in dietPlanEntity.numbersLosesDays) {
            dietPlanEntity.missingDays += 1
            dietPlanEntity.numbersLosesDays.add(dietPlanEntity.currentDay)
        }
    }

    private fun checkNotOpenedDays(days: List<DietDay>): Boolean {
        var isLastDayHandled = false
        var currentTime = Calendar.getInstance().timeInMillis
        var diff = currentTime - dietPlanEntity.timeTrigger
        var loseDays = (diff / ONE_DAY).toInt()
        var oldCurrentDay = dietPlanEntity.currentDay + 1
        dietPlanEntity.currentDay += loseDays
        if (dietPlanEntity.currentDay > days.size - 1) {
            dietPlanEntity.currentDay = days.size - 1
            isLastDayHandled = true
        }
        for (i in oldCurrentDay..dietPlanEntity.currentDay) {
            dietPlanEntity.numbersLosesDays.add(i)
            dietPlanEntity.missingDays += 1
        }
        return isLastDayHandled
    }

    private fun insertInDB() {
        Single.fromCallable {
            App.getInstance().db.dietDAO().insert(this.dietPlanEntity)
            null
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> Log.e("LOL", "saved") }) { obj: Throwable -> obj.printStackTrace() }
    }

    private fun setMeals(days: List<DietDay>) {
        dietPlanEntity.breakfastState = NOT_USE
        dietPlanEntity.lunchState = NOT_USE
        dietPlanEntity.dinnerState = NOT_USE
        dietPlanEntity.snakeState = NOT_USE
        dietPlanEntity.snake2State = NOT_USE
        for (eat in days[dietPlanEntity.currentDay].eats) {
            when (eat.type) {
                0 -> setBreakfast(eat.text)
                1 -> setLunch(eat.text)
                2 -> setDinner(eat.text)
                3 -> setSnack(eat.text)
                4 -> setSecondSnack(eat.text)
            }
        }
    }

    private fun setSecondSnack(text: String) {
        if (text != "") {
            dietPlanEntity.snake2State = NOT_CHECKED
        }
    }

    private fun setSnack(text: String) {
        if (text != "") {
            dietPlanEntity.snakeState = NOT_CHECKED
        }
    }

    private fun setDinner(text: String) {
        if (text != "") {
            dietPlanEntity.dinnerState = NOT_CHECKED
        }
    }

    private fun setLunch(text: String) {
        if (text != "") {
            dietPlanEntity.lunchState = NOT_CHECKED
        }
    }

    private fun setBreakfast(text: String) {
        if (text != "") {
            dietPlanEntity.breakfastState = NOT_CHECKED
        }
    }

    fun isCompletedYesterday(): Boolean {
        return dietPlanEntity.breakfastState != NOT_CHECKED
                && dietPlanEntity.lunchState != NOT_CHECKED
                && dietPlanEntity.dinnerState != NOT_CHECKED
                && dietPlanEntity.snakeState != NOT_CHECKED
                && dietPlanEntity.snake2State != NOT_CHECKED
    }

    fun getCurrentTimeTrigger(): Long {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getTomorrowTimeTrigger(): Long {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis + ONE_DAY
    }

    fun checkEat(type: Int) {
        when (type) {
            0 -> dietPlanEntity.breakfastState = CHECKED
            1 -> dietPlanEntity.lunchState = CHECKED
            2 -> dietPlanEntity.dinnerState = CHECKED
            3 -> dietPlanEntity.snakeState = CHECKED
            4 -> dietPlanEntity.snake2State = CHECKED
        }
        insertInDB()
    }

    fun setEmpty() {
        dietPlanEntity = DietPlanEntity(0, 0, NO_DIET_YET,
                false, false, 0, 0,
                0, 0, 0, 0, 0, 0, mutableListOf())
    }
}