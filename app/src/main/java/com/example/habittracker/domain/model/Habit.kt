package com.example.habittracker.domain.model

import android.os.Parcelable
import com.example.habittracker.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    var uid : String = UNDEFINED_ID,
    var id : Long? = null,
    val title : String,
    val description : String,
    val type : HabitType,
    val habitPriority : HabitPriority,
    val numberExecutions : Int,
    val period : HabitRepetitionPeriod,
    val color : Int,
    val dateCreation : Int
) : Parcelable{
    companion object{
        const val UNDEFINED_ID = ""
    }
}

enum class HabitPriority(val priority : Int){
    HIGH(R.string.text_high),
    MEDIUM(R.string.text_medium),
    LOW(R.string.text_low);

    companion object {
        fun createByPriority(ordinal: Int): HabitPriority {
            return when (ordinal) {
                0 -> MEDIUM
                1 -> LOW
                else -> HIGH
            }
        }
    }
}

enum class HabitType(val type : Int){
    USEFUL(R.string.text_useful),
    HARMFUL(R.string.text_harmful);

    companion object {
        fun createByType(ordinal: Int): HabitType {
            return when (ordinal) {
                1 -> USEFUL
                else -> HARMFUL
            }
        }
    }
}

enum class HabitRepetitionPeriod(val period: Int){
    REGULAR(R.string.text_regular),
    ONE_TIME(R.string.text_on_time)
}
