package com.example.habit_domain.usecase

import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify

@ExtendWith(MockitoExtension::class)
class UpdateHabitUseCaseTest {

    @Mock
    private lateinit var repository: HabitsRepository

    private lateinit var updateHabitUseCase: UpdateHabitUseCase

    @BeforeEach
    fun setUp() {
        updateHabitUseCase = UpdateHabitUseCase(repository = repository)
    }

    @Test
    fun `when status is AVAILABLE, update habit from server`() = runTest {
        val habit = getHabit()
        val status = ConnectivityObserver.Status.AVAILABLE

        updateHabitUseCase.updateHabit(habit = habit, status = status)

        verify(repository).updateHabitFromServer(habit = habit)
    }

    @Test
    fun `when status is UNAVAILABLE, update habit from database`() = runTest{
        val habit = getHabit()
        val status = ConnectivityObserver.Status.UNAVAILABLE

        updateHabitUseCase.updateHabit(habit = habit, status = status)

        verify(repository).updateHabitFromDatabase(habit = habit)
    }

    @Test
    fun `when status is LOSING, update habit from database`() = runTest {
        val habit = getHabit()
        val status = ConnectivityObserver.Status.LOSING

        updateHabitUseCase.updateHabit(habit = habit, status = status)

        verify(repository).updateHabitFromDatabase(habit = habit)
    }

    @Test
    fun `when status is LOST, update habit from database`() = runTest {
        val habit = getHabit()
        val status = ConnectivityObserver.Status.LOST

        updateHabitUseCase.updateHabit(habit = habit, status = status)

        verify(repository).updateHabitFromDatabase(habit = habit)
    }
}