package com.example.habit_data.mappers

import com.example.core.room.entity.HabitEntity
import com.example.core.room.entity.SyncStatus
import com.example.habit_data.modelResponse.HabitDoneResponse
import com.example.habit_data.modelResponse.HabitItem
import com.example.habit_data.modelResponse.HabitResponse
import com.example.habit_domain.model.Habit
import com.example.habit_domain.model.HabitPriority
import com.example.habit_domain.model.HabitRepetitionPeriod
import com.example.habit_domain.model.HabitType
import java.util.Calendar

class HabitRemoteMapper
{

    private fun filterDoneDate(doneDates: List<Int>) : List<Int>{
        val currentCalendar = Calendar.getInstance()

        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = currentCalendar.get(Calendar.MONTH) + 1
        val currentYear = currentCalendar.get(Calendar.YEAR)

        val dateFilter = doneDates.filter { doneDate ->
            currentCalendar.timeInMillis = doneDate.toLong() * 1000

            val day = currentCalendar.get(Calendar.DAY_OF_MONTH)
            val month = currentCalendar.get(Calendar.MONTH) + 1
            val year = currentCalendar.get(Calendar.YEAR)
            day == currentDay && month == currentMonth && year == currentYear
        }

        return  dateFilter
    }

    fun habitToHabitDoneEntitySync(habit: Habit) : HabitEntity{
        var doneHabitList = habit.done_dates.toMutableList()
        val doneDate = (System.currentTimeMillis()/1000).toInt()
        doneHabitList.add(doneDate)
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
            syncStatus = SyncStatus.SYNCED,
            done_dates = doneHabitList
        )
    }

    fun habitToHabitDoneEntityNotSync(habit: Habit) : HabitEntity{
        var doneHabitList = habit.done_dates.toMutableList()
        val doneDate = (System.currentTimeMillis()/1000).toInt()
        doneHabitList.add(doneDate)
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
            done_dates = doneHabitList
        )
    }

    fun habitToHabitDoneResponse(uid : String) : HabitDoneResponse{
        return HabitDoneResponse(
            habit_uid = uid,
            date = (System.currentTimeMillis()/1000).toInt()
        )
    }
    fun habitsResponseToHabits(habitsResponse : HabitResponse) : List<Habit>{ //TODO
        val habits = mutableListOf<Habit>()
        habitsResponse.forEach {response ->
            val habit = Habit(
                uid = response.uid,
                title = response.title,
                description = response.description,
                type = HabitType.codeByType(response.type),
                habitPriority = HabitPriority.codeByPriority(response.priority),
                numberExecutions = response.count,
                period = HabitRepetitionPeriod.codeByPeriod(response.frequency),
                color = response.color,
                dateCreation = response.date,
                done_dates = filterDoneDate(response.done_dates)
            )
            habits.add(habit)
        }
        return habits
    }

    fun habitEntityToHabitItem(entity: HabitEntity) : HabitItem {
        return HabitItem(
            uid = entity.uid,
            title = entity.title,
            description = entity.description,
            type = entity.type,
            priority = entity.period,
            count = entity.numberExecutions,
            frequency = entity.habitPriority,
            color = entity.color,
            date = (System.currentTimeMillis()/1000).toInt(),
            done_dates = emptyList()
        )
    }

    fun habitEntityToHabitItemUpdateServer(entity: HabitEntity) : HabitItem {
        return HabitItem(
            uid = entity.uid,
            title = entity.title,
            description = entity.description,
            type = entity.type,
            priority = entity.period,
            count = entity.numberExecutions,
            frequency = entity.habitPriority,
            color = entity.color,
            date = (System.currentTimeMillis()/1000).toInt(),
            done_dates = entity.done_dates
        )
    }

    fun habitItemToHabitEntity(habit : HabitItem) : HabitEntity{
        return HabitEntity(
            title = habit.title,
            description = habit.description,
            type = habit.type,
            habitPriority = habit.priority,
            numberExecutions = habit.count,
            period = habit.frequency,
            color = habit.color,
            dateCreation = habit.date,
            uid = habit.uid,
            syncStatus = SyncStatus.SYNCED,
            done_dates = habit.done_dates
        )
    }

    fun createHabitToHabitItem(habit : Habit) : HabitItem {
        return HabitItem(
            uid = habit.uid,
            title = habit.title,
            description = habit.description,
            type = HabitType.typeByCode(habit.type),
            priority = HabitRepetitionPeriod.periodByCode(habit.period),
            count = habit.numberExecutions,
            frequency = HabitPriority.priorityByCode(habit.habitPriority),
            color = habit.color,
            date = habit.dateCreation,
            done_dates = emptyList()
        )
    }

    fun updateHabitToHabitItem(habit : Habit) : HabitItem {
        return HabitItem(
            uid = habit.uid,
            title = habit.title,
            description = habit.description,
            type = HabitType.typeByCode(habit.type),
            priority = HabitRepetitionPeriod.periodByCode(habit.period),
            count = habit.numberExecutions,
            frequency = HabitPriority.priorityByCode(habit.habitPriority),
            color = habit.color,
            date = (System.currentTimeMillis()/1000).toInt(),
            done_dates = emptyList()
        )
    }
}