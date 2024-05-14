package com.example.habittracker.presentation.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.habittracker.domain.model.HabitType
import com.example.habittracker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class HabitTypeDialog : DialogFragment()
{
    private var checkedTypeNumber = -1
    interface Host{
        fun typeSelection(text: String)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val singleItems = arrayOf(
            requireContext().getString(HabitType.HARMFUL.type),
            requireContext().getString(HabitType.USEFUL.type))
        val checkedItem = intArrayOf(checkedTypeNumber)
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlayAppMaterialAlertDialog)
            .setTitle(resources.getString(R.string.text_choose_type_habit))
            .setSingleChoiceItems(singleItems, checkedItem[0]) { dialog, which ->
                checkedItem[0] = which
                (parentFragment as Host).typeSelection(singleItems[which])
                dialog.cancel()
            }
            .create()
        return dialog
    }
}