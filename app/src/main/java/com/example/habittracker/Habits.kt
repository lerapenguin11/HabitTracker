package com.example.habittracker

data class Habits(
    var id : Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = -1
    }
}
