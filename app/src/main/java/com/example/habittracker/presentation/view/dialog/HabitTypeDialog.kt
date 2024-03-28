package com.example.habittracker.presentation.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.habittracker.presentation.model.HabitType
import com.example.habittracker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class HabitTypeDialog : DialogFragment()
{
    private var checkedTypeNumber = -1
    interface Host{
        fun typeSelection(text: String)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val singleItems = arrayOf(HabitType.HARMFUL.type, HabitType.USEFUL.type)
        val checkedItem = intArrayOf(checkedTypeNumber)
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.text_choose_type_habit))
            .setNegativeButton(resources.getString(R.string.text_close)) { dialog, which ->
                (parentFragment as Host).typeSelection("")
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.text_gone)) { dialog, which ->
                dialog.cancel()
            }
            .setSingleChoiceItems(singleItems, checkedItem[0]) { dialog, which ->
                checkedItem[0] = which
                (parentFragment as Host).typeSelection(singleItems[which])
            }
            .create()
        return dialog
    }
}