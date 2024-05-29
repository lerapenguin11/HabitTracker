package com.example.habit_domain.usecase

import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GetHabitByIdUseCaseTest {

    @Mock
    private lateinit var repository: HabitsRepository
    private lateinit var getHabitByIdUseCase: GetHabitByIdUseCase

    @BeforeEach
    fun setUp() {
        getHabitByIdUseCase = GetHabitByIdUseCase(repository = repository)
    }

    @Test
    fun `getHabitById returns habit by id when uid is null or empty`() = runTest {
        val habitId = 1L
        val habit = getHabit()
        whenever(repository.getHabitItem(habitId)).thenReturn(flowOf(habit))

        val result = getHabitByIdUseCase.getHabitById(null, habitId).single()

        assertEquals(habit, result)
    }

    @Test
    fun `getHabitById returns habit by UID when uid is not null or empty`() = runTest {
        val uid = "123"
        val habit = getHabit()
        whenever(repository.getHabitItemByUID(uid)).thenReturn(flowOf(habit))

        val result = getHabitByIdUseCase.getHabitById(uid, null).single()

        assertEquals(habit, result)
    }
}