package com.example.habittracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.habittracker.adapter.HabitsAdapter
import com.example.habittracker.databinding.ActivityHabitsBinding

class HabitsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHabitsBinding
    private var habitList : MutableList<Habit> = arrayListOf()
    private val adapter = HabitsAdapter()
    private var startForResult: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleEmptyListMessageVisibility()
        handleSave()
        addHabitClickListener()
    }

    private fun handleSave() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                when (result.resultCode) {
                    CODE_ADD -> {
                        val intent = result.data
                        val newHabit = intent?.getParcelableExtra<Habit>(NEW_HABIT)
                        habitList.add(newHabit!!)
                        handleEmptyListMessageVisibility()
                        setHabitRecyclerView(habitList)
                    }
                    CODE_EDIT -> {
                        val intent = result.data
                        val habit = intent?.getParcelableExtra<Habit>(UPDATE_HABIT)
                        if (habit != null) {
                            habitList[habit.id] = habit
                        }
                        handleEmptyListMessageVisibility()
                        setHabitRecyclerView(habitList)
                    }
                }
            }
    }

    private fun setHabitRecyclerView(habitList: MutableList<Habit>) {
        adapter.submitList(habitList)
        binding.rvHabits.adapter = adapter
        adapter.onHabitListClickListener = {habit ->
            updateHabit(habit)
        }
    }

    private fun updateHabit(habit : Habit) {
        val intent = newIntentEditItem(this, habit)
        startForResult?.launch(intent)
    }

    private fun handleEmptyListMessageVisibility() {
        binding.tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    private fun addHabitClickListener() {
        binding.fabAddHabits.setOnClickListener{
            val intent = newIntentAddItem(this, getHabitId())
            startForResult?.launch(intent)
        }
    }

    private fun getHabitId() : Int{
        if (habitList.isEmpty()){
            return 0
        } else{
            return habitList.last().id + 1
        }
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_HABIT_ITEM_ID = "extra_habit_item_id"
        private const val NEW_HABIT = "new_habit"
        private const val UPDATE_HABIT = "update_habit"
        private const val EXTRA_HABIT_ITEM = "extra_habit_item"
        private const val CODE_ADD = 1
        private const val CODE_EDIT = 2

        fun newIntentAddItem(context: Context, habitId : Int) : Intent {
            val intent = Intent(context, CreateUpdateHabitActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            intent.putExtra(EXTRA_HABIT_ITEM_ID, habitId)
            return intent
        }

        fun newIntentEditItem(context: Context, habit : Habit) : Intent{
            val intent = Intent(context, CreateUpdateHabitActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_HABIT_ITEM, habit)
            return intent
        }
    }
}