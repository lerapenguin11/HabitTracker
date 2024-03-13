package com.example.habittracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habittracker.databinding.ActivityCreateUpdateHabitBinding

class CreateUpdateHabitActivity : AppCompatActivity() {

    private var screenMode = MODE_UNKNOWN
    private var shopID = Habits.UNDEFINED_ID

    private lateinit var binding : ActivityCreateUpdateHabitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUpdateHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseParams()
        launchRightMode()
    }

    private fun parseParams(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra((EXTRA_SCREEN_MODE))
        if (mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if(screenMode == MODE_EDIT){
            if (!intent.hasExtra(EXTRA_HABIT_ITEM_ID)){
                throw RuntimeException("Param shop id is absent")
            }
            shopID = intent.getIntExtra(EXTRA_HABIT_ITEM_ID, Habits.UNDEFINED_ID)
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode() {
        binding.tvTitle.text = "Создать привычку"
    }

    private fun launchEditMode() {
        binding.tvTitle.text = "Редактировать привычку"
    }

    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_HABIT_ITEM_ID = "extra_habit_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
    }
}