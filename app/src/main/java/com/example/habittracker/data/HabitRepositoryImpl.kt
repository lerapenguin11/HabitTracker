package com.example.habittracker.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.presentation.model.Habit

class HabitRepositoryImpl : HabitsRepository {
    private val habitListLD = MutableLiveData<List<Habit>>()
    private val habitList = mutableListOf<Habit>()

    override fun getHabits(): LiveData<List<Habit>> {
        return habitListLD
    }

    override fun getHabitItem(habitId: Int): Habit {
        return habitList.find {
            it.id == habitId
        }!!
    }

    override fun updateHabit(habit: Habit) {
        val habitIndex = habitList.indexOfFirst { it.id == habit.id }
        if (habitIndex != -1) {
            habitList[habitIndex] = habit
        }
    }

    override fun createHabit(newHabit: Habit) {
        habitList.add(newHabit)
        updateList()
    }

    private fun updateList(){
        habitListLD.value = habitList.toList()
    }
}