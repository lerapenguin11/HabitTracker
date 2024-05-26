package com.example.habit_data.api

import com.example.habit_data.modelResponse.HabitItem
import com.example.habit_data.modelResponse.HabitResponse
import com.example.habit_data.modelResponse.HabitUIDResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface HabitsApi
{
    @GET("habit")
    suspend fun getAllHabits() : Response<HabitResponse>

    @PUT("habit")
    suspend fun editHabit(@Body habit : HabitItem) : Response<HabitUIDResponse>

    @PUT("habit")
    suspend fun createHabit(@Body newHabit : HabitItem) : Response<HabitUIDResponse>

    @DELETE("habit")
    suspend fun deleteHabit(@Body uid : String) //TODO: удаление привычки
}