package com.example.habittracker.data.api

import com.example.habittracker.data.modelResponse.HabitItem
import com.example.habittracker.data.modelResponse.HabitResponse
import com.example.habittracker.data.modelResponse.HabitUIDResponse
import kotlinx.coroutines.flow.Flow
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
    suspend fun editHabit(@Body habit : HabitItem) : HabitUIDResponse//TODO: редактирование привычки

    @PUT("habit")
    suspend fun createHabit(@Body newHabit : HabitItem) : Response<HabitUIDResponse>//TODO: создание привычки

    @DELETE("habit")
    suspend fun deleteHabit(@Body uid : String) //TODO: удаление привычки
}