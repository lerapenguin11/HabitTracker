package com.example.habittracker.data.api

import com.example.habittracker.data.modelResponse.HabitItem
import com.example.habittracker.data.modelResponse.HabitResponse
import com.example.habittracker.data.modelResponse.HabitUIDResponse
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
}