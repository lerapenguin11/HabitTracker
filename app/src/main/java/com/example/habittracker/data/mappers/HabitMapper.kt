package com.example.habittracker.data.mappers

import com.example.habittracker.data.entity.HabitEntity
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
            type = HabitType.typeByCode(habit.type),
            habitPriority = HabitPriority.priorityByCode(habit.habitPriority),
            numberExecutions = habit.numberExecutions,
            period = HabitRepetitionPeriod.periodByCode(habit.period),
            color = habit.color,
            dateCreation = habit.dateCreation,
            uid = uid
        )
    }

    fun updateHabitToHabitEntityRemoteTest(habit: Habit, uid : String) : HabitEntity{
        return HabitEntity(
            title = habit.title,
            description = habit.description,
            type = HabitType.typeByCode(habit.type),
            habitPriority = HabitPriority.priorityByCode(habit.habitPriority),
            numberExecutions = habit.numberExecutions,
            period = HabitRepetitionPeriod.periodByCode(habit.period),
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
            type = HabitType.typeByCode(habit.type),
            habitPriority = HabitPriority.priorityByCode(habit.habitPriority),
            numberExecutions = habit.numberExecutions,
            period = HabitRepetitionPeriod.periodByCode(habit.period),
            color = habit.color,
            dateCreation = habit.dateCreation,
            uid = habit.uid
        )
    }

    fun insertHabitToHabitEntity(habit: Habit) : HabitEntity{
        return HabitEntity(
            title = habit.title,
            description = habit.description,
            type = HabitType.typeByCode(habit.type),
            habitPriority = HabitPriority.priorityByCode(habit.habitPriority),
            numberExecutions = habit.numberExecutions,
            period = HabitRepetitionPeriod.periodByCode(habit.period),
            color = habit.color,
            dateCreation = habit.dateCreation,
            uid = habit.uid
        )
    }

    fun habitEntityToHabit(entity: HabitEntity): Habit {
        return Habit(
            uid = entity.uid,
            title = entity.title,
            description = entity.description,
            type = HabitType.codeByType(entity.type),
            habitPriority = HabitPriority.codeByPriority(entity.habitPriority),
            numberExecutions = entity.numberExecutions,
            period = HabitRepetitionPeriod.codeByPeriod(entity.period),
            color = entity.color,
            dateCreation = entity.dateCreation
        )
    }
    fun habitsEntityToHabits(habitEntities : List<HabitEntity>) : List<Habit>{
        val habits = arrayListOf<Habit>()
        for (entity in habitEntities){
            val habit = Habit(
                uid = entity.uid,
                title = entity.title,
                description = entity.description,
                type = HabitType.codeByType(entity.type),
                habitPriority = HabitPriority.codeByPriority(entity.habitPriority),
                numberExecutions = entity.numberExecutions,
                period = HabitRepetitionPeriod.codeByPeriod(entity.period),
                color = entity.color,
                dateCreation = entity.dateCreation,
                id = entity.id
            )
            habits.add(habit)
        }
        return habits
    }
}