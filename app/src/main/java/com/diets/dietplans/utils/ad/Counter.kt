package com.diets.dietplans.utils.ad

class Counter {

    private var counter = 0

    companion object{
        var counter : Counter? = null

        fun getInstance() : Counter{
            if (counter == null){
                counter = Counter()
            }
            return counter!!
        }
    }

    fun adToCounter(){
        counter ++
    }

    fun getCounter() : Int{
        return counter
    }
}