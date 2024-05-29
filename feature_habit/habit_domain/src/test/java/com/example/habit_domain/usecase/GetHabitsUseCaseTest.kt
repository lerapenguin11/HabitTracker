package com.example.habit_domain.usecase

import com.example.core.network.ResultData
import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GetHabitsUseCaseTest {

    @Mock
    private lateinit var repository: HabitsRepository
    private lateinit var getHabitUseCase: GetHabitsUseCase

    @BeforeEach
    fun setUp(){
        getHabitUseCase = GetHabitsUseCase(repository = repository)
    }

    @Test
    fun `when status is AVAILABLE, returns habit from server`() = runTest {
        val status = ConnectivityObserver.Status.AVAILABLE
        val habits = listOf<Habit>()
        val fakeResult = ResultData.Success(habits)

        whenever(repository.getHabitsFromServer()).thenReturn(ResultData.Success(habits))
        val result = getHabitUseCase.getHabits(status = status).single()
        verify(repository).getHabitsFromServer()
        assert(result == fakeResult)
    }

    @Test
    fun `when status is UNAVAILABLE, returns habit from database`() = runTest {
        val status = ConnectivityObserver.Status.UNAVAILABLE
        val habits = listOf<Habit>()
        val fakeResult = flow { emit(habits) }

        whenever(repository.getHabitsFromDatabase()).thenReturn(fakeResult)
        val result = getHabitUseCase.getHabits(status = status)
        verify(repository).getHabitsFromDatabase()

        result.collect{
            assert(it is ResultData.Success && it.data == habits)
        }
    }

    @Test
    fun `when status is LOSING, returns habit from database`() = runTest {
        val status = ConnectivityObserver.Status.LOSING
        val habits = listOf<Habit>()
        val fakeResult = flow { emit(habits) }

        whenever(repository.getHabitsFromDatabase()).thenReturn(fakeResult)
        val result = getHabitUseCase.getHabits(status = status)
        verify(repository).getHabitsFromDatabase()

        result.collect{
            assert(it is ResultData.Success && it.data == habits)
        }
    }

    @Test
    fun `when status is LOST, returns habit from database`() = runTest {
        val status = ConnectivityObserver.Status.LOST
        val habits = listOf<Habit>()
        val fakeResult = flow { emit(habits) }

        whenever(repository.getHabitsFromDatabase()).thenReturn(fakeResult)
        val result = getHabitUseCase.getHabits(status = status)
        verify(repository).getHabitsFromDatabase()

        result.collect{
            assert(it is ResultData.Success && it.data == habits)
        }
    }
}