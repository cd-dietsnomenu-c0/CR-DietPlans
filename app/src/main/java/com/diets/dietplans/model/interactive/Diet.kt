package com.diets.dietplans.model.interactive

import java.io.Serializable

data class Diet(var title: String, var kcal : Int, var shortIntroduction: String, var introduction: String,
                var mainImage: String, var benefitsTitle: String,
                var benefits: List<String>, var consTitle: String,
                var cons: List<String>, var menuTitle: String,
                var menuText: String, var days: List<DietDay>, var hintText: String, var resultText: String,
                var review: Review,
                var isNew : Boolean, var index : Int) : Serializable {

    constructor() : this("", 0, "", "", "", "", listOf(), "", listOf(), "", "", listOf(), "", "", Review(), false, 0)
}