package com.example.habittracker.presentation.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

data class FilterParameters(
    var habitTitle : String?,
    var habitDescription : String?,
    var habitFrequency : String?,
    var oldDate : Boolean?,
    var newDate : Boolean?
)
