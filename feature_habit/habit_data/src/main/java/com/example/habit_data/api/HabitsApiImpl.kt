package com.example.habit_data.api

import com.example.habit_data.modelResponse.HabitDoneResponse
import com.example.habit_data.modelResponse.HabitItem
import com.example.habit_data.modelResponse.HabitResponse
import com.example.habit_data.modelResponse.HabitUIDResponse
import retrofit2.Response
import retrofit2.Retrofit

class HabitsApiImpl(retrofit: Retrofit) : HabitsApi {
    private val apiService = retrofit.create(HabitsApi::class.java)

    override suspend fun getAllHabits(): Response<HabitResponse> {
        return apiService.getAllHabits()
    }

    override suspend fun editHabit(habit: HabitItem) : Response<HabitUIDResponse>{
        return apiService.editHabit(habit = habit)
    }

    override suspend fun createHabit(newHabit: HabitItem) : Response<HabitUIDResponse>{
        return apiService.createHabit(newHabit = newHabit)
    }

    override suspend fun deleteHabit(uid: String) {
        return apiService.deleteHabit(uid = uid)
    }

    override suspend fun doneHabit(habitDoneResponse: HabitDoneResponse) {
        return apiService.doneHabit(habitDoneResponse = habitDoneResponse)
    }
}