package com.diets.dietplans.model.interactive

import java.io.Serializable

data class Review (var name : String, var avatar : String, var text : String)  : Serializable {
    constructor() : this ("", "", "")
}