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
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class PerformHabitUseCaseTest {

    @Mock
    private lateinit var repository: HabitsRepository
    private lateinit var performHabitUseCase: PerformHabitUseCase

    @BeforeEach
    fun setUp(){
        performHabitUseCase = PerformHabitUseCase(repository = repository)
    }

    @Test
    fun `performHabit calls performHabitFromServer when status is AVAILABLE and uid is not null or empty`() = runTest {
        val status = ConnectivityObserver.Status.AVAILABLE
        val habit = getHabitIdNull()

        whenever(repository.performHabitFromServer(habit = habit)).thenReturn(Unit)
        performHabitUseCase.performHabit(habit = habit, status = status)
        verify(repository).performHabitFromServer(habit = habit)
    }

    @Test
    fun `performHabit calls performHabitFromDatabase when status is UNAVAILABLE and id is not null`() = runTest{
        val status = ConnectivityObserver.Status.UNAVAILABLE
        val habit = getHabitUidIsEmpty()

        whenever(repository.performHabitFromDatabase(habit = habit)).thenReturn(Unit)
        performHabitUseCase.performHabit(habit = habit, status = status)
        verify(repository).performHabitFromDatabase(habit = habit)
    }

    @Test
    fun `performHabit calls performHabitFromDatabase when status is LOSING and id is not null`() = runTest{
        val status = ConnectivityObserver.Status.LOSING
        val habit = getHabitUidIsEmpty()

        whenever(repository.performHabitFromDatabase(habit = habit)).thenReturn(Unit)
        performHabitUseCase.performHabit(habit = habit, status = status)
        verify(repository).performHabitFromDatabase(habit = habit)
    }

    @Test
    fun `performHabit calls performHabitFromDatabase when status is LOST and id is not null`() = runTest{
        val status = ConnectivityObserver.Status.LOST
        val habit = getHabitUidIsEmpty()

        whenever(repository.performHabitFromDatabase(habit = habit)).thenReturn(Unit)
        performHabitUseCase.performHabit(habit = habit, status = status)
        verify(repository).performHabitFromDatabase(habit = habit)
    }
}