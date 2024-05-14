package com.example.habittracker.data.mappers

import com.example.habittracker.data.entity.HabitEntity
import com.example.habittracker.data.modelResponse.HabitResponse
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitPriority
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.domain.model.HabitType

class HabitMapper
{
    //TODO:HabitMapperRemote
    fun habitsResponseToHabits(habitsResponse : HabitResponse) : List<Habit>{
        val habits = mutableListOf<Habit>()
        habitsResponse.forEach {response ->
            val habit = getSelectedHabitPeriod(response.frequency)?.let { period ->
                Habit(
                    id = response.uid,
                    title = response.title,
                    description = response.description,
                    type = HabitType.createByType(response.type),
                    habitPriority = HabitPriority.createByPriority(response.priority),
                    numberExecutions = response.count,
                    period = period,
                    color = response.color,
                    dateCreation = response.date
                )
            }
            habit?.let { habits.add(it) }
        }
        return habits
    }

    //TODO:HabitMapperLocal
    fun updateHabitToHabitEntity(habit: Habit) : HabitEntity{
        return HabitEntity(
            id = habit.id.toLong(),
            title = habit.title,
            description = habit.description,
            type = habit.type.type,
            habitPriority = habit.habitPriority.priority,
            numberExecutions = habit.numberExecutions,
            period = habit.period.period,
            color = habit.color,
            dateCreation = habit.dateCreation
        )
    }

    fun insertHabitToHabitEntity(habit: Habit) : HabitEntity{
        return HabitEntity(
            title = habit.title,
            description = habit.description,
            type = habit.type.type,
            habitPriority = habit.habitPriority.priority,
            numberExecutions = habit.numberExecutions,
            period = habit.period.period,
            color = habit.color,
            dateCreation = habit.dateCreation
        )
    }

    fun habitEntityToHabit(entity: HabitEntity) : Habit{
        val model = entity.id.let { id ->
            getSelectedHabitPeriod(entity.period)?.let { period ->
                getSelectedHabitPriority(entity.habitPriority)?.let { habitPriority ->
                    getSelectedHabitType(entity.type)?.let { type ->
                        id?.let { id ->
                            Habit(
                                id = id.toString(),
                                title = entity.title,
                                description = entity.description,
                                type = type,
                                habitPriority = habitPriority,
                                numberExecutions = entity.numberExecutions,
                                period = period,
                                color = entity.color,
                                dateCreation = entity.dateCreation
                            )
                        }
                    }
                }
            }
        }
        return model!!
    }
    fun habitsEntityToHabits(habitEntities : List<HabitEntity>) : List<Habit>{
        val habits = arrayListOf<Habit>()
        for (entity in habitEntities){
            val habit = getSelectedHabitPeriod(entity.period)?.let { period ->
                getSelectedHabitPriority(entity.habitPriority)?.let { habitPriority ->
                    getSelectedHabitType(entity.type)?.let { type ->
                        Habit(
                            id = entity.id.toString(),
                            title = entity.title,
                            description = entity.description,
                            type = type,
                            habitPriority = habitPriority,
                            numberExecutions = entity.numberExecutions,
                            period = period,
                            color = entity.color,
                            dateCreation = entity.dateCreation
                        )
                    }
                }
            }
            habit?.let { habits.add(it) }
        }
        return habits
    }

    private fun getSelectedHabitType(type : Int) : HabitType? {
        return when(type){
            HabitType.USEFUL.type -> HabitType.USEFUL
            HabitType.HARMFUL.type -> HabitType.HARMFUL
            else -> {null}
        }
    }

    private fun getSelectedHabitPriority(habitPriority : Int) : HabitPriority?{
        return when(habitPriority){
            HabitPriority.HIGH.priority -> HabitPriority.HIGH
            HabitPriority.LOW.priority -> HabitPriority.LOW
            HabitPriority.MEDIUM.priority -> HabitPriority.MEDIUM
            else -> null
        }
    }

    private fun getSelectedHabitPeriod(period : Int): HabitRepetitionPeriod? {
        return when(period){
            HabitRepetitionPeriod.REGULAR.period -> HabitRepetitionPeriod.REGULAR
            HabitRepetitionPeriod.ONE_TIME.period -> HabitRepetitionPeriod.ONE_TIME
            else -> null
        }
    }
}