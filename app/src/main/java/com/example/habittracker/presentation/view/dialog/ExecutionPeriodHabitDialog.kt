package com.example.habittracker.presentation.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class ExecutionPeriodHabitDialog : DialogFragment(){

    interface Host{
        fun selectionExecutionPeriod(text : String)
    }

    private var checkedPeriodNumber = -1

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        val singleItems = arrayOf(HabitRepetitionPeriod.REGULAR.period,
            HabitRepetitionPeriod.ONE_TIME.period)
        val checkedItem = intArrayOf(checkedPeriodNumber)
        val dialog = MaterialAlertDialogBuilder(requireContext(),
            R.style.ThemeOverlayAppMaterialAlertDialog)
            .setTitle(resources.getString(R.string.text_choose_period_habit))
            .setSingleChoiceItems(singleItems, checkedItem[0]) { dialog, which ->
                checkedItem[0] = which
                (parentFragment as Host).selectionExecutionPeriod(singleItems[which])
                dialog.cancel()
            }
            .create()
        return dialog
    }
}