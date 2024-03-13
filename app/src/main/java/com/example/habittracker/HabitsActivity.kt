package com.example.habittracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habittracker.databinding.ActivityHabitsBinding

class HabitsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHabitsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addHabitClickListener()
    }

    private fun addHabitClickListener() {
        binding.fabAddHabits.setOnClickListener{
            val intent = newIntentAddItem(this)
            startActivity(intent)
        }
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_HABIT_ITEM_ID = "extra_habit_item_id"

        fun newIntentAddItem(context: Context) : Intent {
            val intent = Intent(context, CreateUpdateHabitActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId : Int) : Intent{
            val intent = Intent(context, CreateUpdateHabitActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_HABIT_ITEM_ID, shopItemId)
            return intent
        }
    }
}