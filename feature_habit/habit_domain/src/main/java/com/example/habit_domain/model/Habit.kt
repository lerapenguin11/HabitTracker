package com.example.habit_domain.model

import android.os.Parcelable
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
    val dateCreation : Int,
    val done_dates: List<Int>
) : Parcelable{
    companion object{
        const val UNDEFINED_ID = ""
    }

    override fun equals(other: Any?): Boolean {
        if (other == this) {
            return true;
        }

        if (other is Habit){
            return (
                    (this.uid == other.uid) &&
                    (this.id == other.id) &&
                    (this.title == other.title) &&
                    (this.description == other.description) &&
                    (this.type == other.type) &&
                    (this.habitPriority == other.habitPriority) &&
                    (this.numberExecutions == other.numberExecutions) &&
                    (this.period == other.period) &&
                    (this.color == other.color) &&
                    (this.dateCreation == other.dateCreation)
                    )
        }
        
        return super.equals(other)
    }
}



enum class HabitPriority(val priority : String){
    HIGH("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий");

    companion object {
        fun codeByPriority(ordinal: Int): HabitPriority {
            return when (ordinal) {
                0 -> MEDIUM
                1 -> LOW
                else -> HIGH
            }
        }

        fun priorityByCode(ordinal : HabitPriority) : Int{
            return when(ordinal){
                MEDIUM -> 0
                LOW -> 1
                HIGH -> 2
            }
        }

        fun lineByPriority(ordinal: String) : HabitPriority {
            return when(ordinal){
                MEDIUM.priority -> MEDIUM
                LOW.priority -> LOW
                else -> {
                    HIGH
                }
            }
        }
    }
}

enum class HabitType(val type : String){
    USEFUL("Полезная"),
    HARMFUL("Вредная");

    companion object {
        fun codeByType(ordinal: Int): HabitType {
            return when (ordinal) {
                1 -> USEFUL
                else -> HARMFUL
            }
        }

        fun typeByCode(ordinal: HabitType) : Int{
            return when(ordinal){
                HARMFUL -> 0
                USEFUL -> 1
            }
        }

        fun lineByType(ordinal: String) : HabitType {
            return when(ordinal){
                HARMFUL.type -> HARMFUL
                else -> {
                    USEFUL
                }
            }
        }
    }
}

enum class HabitRepetitionPeriod(val period: String){
    REGULAR("Регулярная"),
    ONE_TIME("Разовая");

    companion object{
        fun codeByPeriod(ordinal: Int) : HabitRepetitionPeriod {
            return when(ordinal){
                0 -> REGULAR
                else -> ONE_TIME
            }
        }

        fun periodByCode(ordinal : HabitRepetitionPeriod) : Int{
            return when(ordinal){
                REGULAR -> 0
                ONE_TIME -> 1
            }
        }

        fun lineByPeriod(ordinal: String) : HabitRepetitionPeriod {
            return when(ordinal){
                REGULAR.period -> REGULAR
                else -> {
                    ONE_TIME
                }
            }
        }
    }
}
