package com.example.habittracker.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.presentation.model.Habit

object HabitRepositoryImpl : HabitsRepository {
    private val habitListLD = MutableLiveData<List<Habit>>()
    private val habitList = mutableListOf<Habit>()

    override fun getHabits(): LiveData<List<Habit>> {
        return habitListLD
    }

    //-------TODO: вынести в HabitProcessingRepositoryImpl------
    override fun getHabitItem(habitId: Int): Habit {
        return habitList.find {
            it.id == habitId
        }!!
    }

    @SuppressLint("NullSafeMutableLiveData")
    override fun updateHabit(habit: Habit) {
        val oldHabitList = habitList
        val habitIndex = oldHabitList.indexOfFirst { it.id == habit.id }
        if (habitIndex != -1) {
            oldHabitList[habitIndex] = habit
            updateList()
        }
    }

    override fun createHabit(newHabit: Habit) {
        habitList.add(newHabit)
        updateList()
    }
    //-------TODO: вынести в HabitProcessingRepositoryImpl------

    //TODO: удалить
    private fun updateList(){
        habitListLD.value = habitList
        println("habitListLD: ${habitListLD.value}")
    }
}