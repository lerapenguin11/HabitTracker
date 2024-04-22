package com.example.habittracker.data.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.data.entity.HabitEntity
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitPriority
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.domain.model.HabitType

class HabitMapper
{
    fun habitToHabitEntity(habit: Habit) : HabitEntity{
        return HabitEntity(
            title = habit.title,
            description = habit.description,
            type = habit.type.type,
            habitPriority = habit.habitPriority.priority,
            numberExecutions = habit.numberExecutions,
            period = habit.period.period,
            color = habit.color
        )
    }

    fun habitEntityToHabitLD(entity: LiveData<HabitEntity>) : LiveData<Habit>{
        val habitLD = MutableLiveData<Habit>()
        val model = entity.value?.id?.let { id ->
            getSelectedHabitPeriod(entity.value!!.period)?.let { period ->
                getSelectedHabitPriority(entity.value!!.habitPriority)?.let { habitPriority ->
                    getSelectedHabitType(entity.value!!.type)?.let { type ->
                        Habit(
                            id = id,
                            title = entity.value!!.title,
                            description = entity.value!!.description,
                            type = type,
                            habitPriority = habitPriority,
                            numberExecutions = entity.value!!.numberExecutions,
                            period = period,
                            color = entity.value!!.color
                        )
                    }
                }
            }
        }
        habitLD.value = model!!
        return habitLD
    }
    fun habitsEntityToHabits(entity : List<HabitEntity>) : List<Habit>{
        val list = arrayListOf<Habit>()
        for (i in entity){
            val id = i.id
            val title = i.title
            val description = i.description
            val type = i.type
            val habitPriority = i.habitPriority
            val numberExecutions = i.numberExecutions
            val period = i.period
            val color = i.color

            val model = getSelectedHabitPeriod(period)?.let { period ->
                getSelectedHabitPriority(habitPriority)?.let { habitPriority ->
                    getSelectedHabitType(type)?.let { type ->
                        Habit(
                            id = id!!,
                            title = title,
                            description = description,
                            type = type,
                            habitPriority = habitPriority,
                            numberExecutions = numberExecutions,
                            period = period,
                            color = color
                        )
                    }
                }
            }
            model?.let { list.add(it) }
        }
        return list
    }

    private fun getSelectedHabitType(type : String) : HabitType? {
        return when(type){
            HabitType.USEFUL.type -> HabitType.USEFUL
            HabitType.HARMFUL.type -> HabitType.HARMFUL
            else -> {null}
        }
    }

    private fun getSelectedHabitPriority(habitPriority : String) : HabitPriority?{
        return when(habitPriority){
            HabitPriority.HIGH.priority -> HabitPriority.HIGH
            HabitPriority.LOW.priority -> HabitPriority.LOW
            HabitPriority.MEDIUM.priority -> HabitPriority.MEDIUM
            else -> null
        }
    }

    private fun getSelectedHabitPeriod(period : String): HabitRepetitionPeriod? {
        return when(period){
            HabitRepetitionPeriod.REGULAR.period -> HabitRepetitionPeriod.REGULAR
            HabitRepetitionPeriod.ONE_TIME.period -> HabitRepetitionPeriod.ONE_TIME
            else -> null
        }
    }
}