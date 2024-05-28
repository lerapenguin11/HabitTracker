package com.example.habit_data.mappers

import com.example.core.room.entity.HabitEntity
import com.example.core.room.entity.SyncStatus
import com.example.habit_data.modelResponse.HabitUIDResponse
import com.example.habit_domain.model.Habit
import com.example.habit_domain.model.HabitPriority
import com.example.habit_domain.model.HabitRepetitionPeriod
import com.example.habit_domain.model.HabitType
import com.example.habit_domain.model.HabitUID

class HabitMapper
{
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
            uid = uid,
            syncStatus = SyncStatus.SYNCED,
            done_dates = habit.done_dates
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
            id = habit.id,
            syncStatus = SyncStatus.SYNCED,
            done_dates = habit.done_dates
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
            uid = entity.uid,
            done_dates = entity.done_dates
        )
    }

    //TODO:HabitMapperLocal
    fun updateHabitToHabitEntityNotSync(habit: Habit) : HabitEntity{
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
            uid = habit.uid,
            syncStatus = SyncStatus.PENDING_DOWNLOAD,
            done_dates = habit.done_dates
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
            uid = habit.uid,
            syncStatus = SyncStatus.PENDING_UPLOAD,
            done_dates = habit.done_dates
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
            dateCreation = entity.dateCreation,
            done_dates = entity.done_dates
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
                id = entity.id,
                done_dates = entity.done_dates
            )
            habits.add(habit)
        }
        return habits
    }
}