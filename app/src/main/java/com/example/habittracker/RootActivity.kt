package com.example.habittracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.databinding.ActivityRootBinding
import com.example.habittracker.presentation.model.Habit

class RootActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}