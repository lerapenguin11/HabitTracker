package com.example.habit_domain.usecase

import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class CreateHabitUseCaseTest {

    @Mock
    private lateinit var repository: HabitsRepository
    private lateinit var createHabitUseCase: CreateHabitUseCase

    @BeforeEach
    fun setUp(){
        createHabitUseCase = CreateHabitUseCase(repository = repository)
    }

    @Test
    fun `when status is AVAILABLE, create habit from server`() = runTest {
        val habit = getHabit()
        val status = ConnectivityObserver.Status.AVAILABLE

        createHabitUseCase.createHabit(newHabit = habit, status = status)
        verify(repository).createHabitFromServer(habit = habit)
    }

    @Test
    fun `when status is UNAVAILABLE, create habit from database`() = runTest {
        val habit = getHabit()
        val status = ConnectivityObserver.Status.UNAVAILABLE

        createHabitUseCase.createHabit(newHabit = habit, status = status)
        verify(repository).createHabitFromDatabase(newHabit = habit)
    }

    @Test
    fun `when status is LOSING, create habit from database`() = runTest {
        val habit = getHabit()
        val status = ConnectivityObserver.Status.LOSING

        createHabitUseCase.createHabit(newHabit = habit, status = status)
        verify(repository).createHabitFromDatabase(newHabit = habit)
    }

    @Test
    fun `when status is LOST, create habit from database`() = runTest {
        val habit = getHabit()
        val status = ConnectivityObserver.Status.LOST

        createHabitUseCase.createHabit(newHabit = habit, status = status)
        verify(repository).createHabitFromDatabase(newHabit = habit)
    }
}