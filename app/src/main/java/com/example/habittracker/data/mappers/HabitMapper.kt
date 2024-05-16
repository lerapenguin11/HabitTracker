package com.example.habittracker.data.mappers

import com.example.habittracker.data.entity.HabitEntity
import com.example.habittracker.data.modelResponse.HabitItem
import com.example.habittracker.data.modelResponse.HabitResponse
import com.example.habittracker.data.modelResponse.HabitUIDResponse
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitPriority
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.domain.model.HabitType
import com.example.habittracker.domain.model.HabitUID

class HabitMapper
{
    //TODO:HabitMapperRemote




    fun insertHabitToHabitEntityRemoteTest(habit: Habit, uid : String) : HabitEntity{
        return HabitEntity(
            title = habit.title,
            description = habit.description,
            type = habit.type.type,
            habitPriority = habit.habitPriority.priority,
            numberExecutions = habit.numberExecutions,
            period = habit.period.period,
            color = habit.color,
            dateCreation = habit.dateCreation,
            uid = uid
        )
    }

    fun updateHabitToHabitEntityRemoteTest(habit: Habit, uid : String) : HabitEntity{
        return HabitEntity(
            title = habit.title,
            description = habit.description,
            type = habit.type.type,
            habitPriority = habit.habitPriority.priority,
            numberExecutions = habit.numberExecutions,
            period = habit.period.period,
            color = habit.color,
            dateCreation = habit.dateCreation,
            uid = uid,
            id = habit.id
        )
    }

    fun habitUIDResponseToHabitUID(habitUID: HabitUIDResponse) : HabitUID{
        return HabitUID(uid = habitUID.uid)
    }

    fun habitToHabitEntityRemote(entity: HabitEntity) : Habit{
        return Habit(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            type = HabitType.codeByType(entity.type),
            habitPriority = HabitPriority.codeByPriority(entity.habitPriority),
            numberExecutions = entity.numberExecutions,
            period = HabitRepetitionPeriod.codeByPeriod(entity.period),
            color = entity.color,
            dateCreation = entity.dateCreation,
            uid = entity.uid
        )
    }

    //TODO:HabitMapperLocal
    fun updateHabitToHabitEntity(habit: Habit) : HabitEntity{
        return HabitEntity(
            id = habit.id,
            title = habit.title,
            description = habit.description,
            type = habit.type.type,
            habitPriority = habit.habitPriority.priority,
            numberExecutions = habit.numberExecutions,
            period = habit.period.period,
            color = habit.color,
            dateCreation = habit.dateCreation,
            uid = habit.uid
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
            dateCreation = habit.dateCreation,
            uid = habit.uid
        )
    }

    fun habitEntityToHabit(entity: HabitEntity) : Habit{
        val model = entity.id.let { id ->
            getSelectedHabitPeriod(entity.period)?.let { period ->
                getSelectedHabitPriority(entity.habitPriority)?.let { habitPriority ->
                    getSelectedHabitType(entity.type)?.let { type ->
                        id?.let { id ->
                            Habit(
                                uid = id.toString(),
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
                            uid = entity.id.toString(),
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