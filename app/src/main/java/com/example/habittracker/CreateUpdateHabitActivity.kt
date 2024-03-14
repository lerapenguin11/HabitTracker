package com.example.habittracker

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.databinding.ActivityCreateUpdateHabitBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class CreateUpdateHabitActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateUpdateHabitBinding
    private var screenMode = MODE_UNKNOWN
    private var habitID = Habits.UNDEFINED_ID
    private var itemArrayExecutions : String? = null
    private var itemArrayPriority : String? = null
    private var habit : Habits? = null
    private var checkedTypeNumber = -1
    private var checkedPeriodNumber = -1
    private var color : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUpdateHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseParams()
        launchRightMode()
        textInputEditTextClickListener()
        checkingButtonActivity()
        saveHabit()
        binding.btClose.setOnClickListener { finish() }
    }

    private fun saveHabit() {
        binding.btSaveHabit.setOnClickListener {
            when(screenMode){
                MODE_ADD -> intentAddHabit()
                MODE_EDIT -> intentUpdateHabit()
            }
        }
    }

    private fun intentUpdateHabit() {
        val habit = getHabit()
        setResult(CODE_EDIT, Intent().putExtra(UPDATE_HABIT, habit))
        finish()
    }

    private fun intentAddHabit() {
        val newHabit = getHabit()
        setResult(CODE_ADD, Intent().putExtra(NEW_HABIT, newHabit))
        finish()
    }

    private fun checkingButtonActivity() {
        val textChangedListenerAdd = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btSaveHabit.isEnabled = textFieldCheck() }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.tilNameHabit.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilDescHabit.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilPriority.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilType.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilNumberExecutions.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilFrequency.editText?.addTextChangedListener(textChangedListenerAdd)
    }

    private fun textFieldCheck(): Boolean {
        return (binding.tilNameHabit.editText?.length() != 0 &&
                binding.tilDescHabit.editText?.length() != 0 &&
                binding.tilPriority.editText?.length() != 0 &&
                binding.tilType.editText?.length() != 0 &&
                binding.tilNumberExecutions.editText?.length() !=0 &&
                binding.tilFrequency.editText?.length() != 0)
    }

    private fun textInputEditTextClickListener() {
        binding.tiEtTypeHabit.setOnClickListener { openTypeHabitDialog() }
        binding.tiEtFrequency.setOnClickListener { openPeriodExecutionHabitDialog() }
        binding.tiEtColorCard.setOnClickListener { openColorPickerDialog() }
        setArrayPriority()
        setArrayNumberExecutions()
    }

    private fun setArrayNumberExecutions() {
        val items = mutableListOf<String>()
        for (i in 1 until 11){ items.add("$i") }
        val adapter = ArrayAdapter(this, R.layout.item_priority, items)
        binding.tvArrayExecutions.threshold = 1
        binding.tvArrayExecutions.setAdapter(adapter)
        binding.tvArrayExecutions.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            itemArrayExecutions = parent.getItemAtPosition(position).toString()
        }
    }

    private fun setArrayPriority() {
        val items = listOf(
            PriorityHabit.HIGH.priority, PriorityHabit.MEDIUM.priority, PriorityHabit.LOW.priority)
        val adapter = ArrayAdapter(this, R.layout.item_priority, items)
        binding.tvArrayPriority.threshold = 1
        binding.tvArrayPriority.setAdapter(adapter)
        binding.tvArrayPriority.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            itemArrayPriority = parent.getItemAtPosition(position).toString()
        }
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
        if(screenMode == MODE_ADD) {
            if (!intent.hasExtra(EXTRA_HABIT_ITEM_ID)) {
                throw RuntimeException("Param habit id is absent")
            }
            habitID = intent.getIntExtra(EXTRA_HABIT_ITEM_ID, Habits.UNDEFINED_ID)
        }
        if(screenMode == MODE_EDIT){
            if (!intent.hasExtra(EXTRA_HABIT_ITEM)){
                throw RuntimeException("Param habit id is absent")
            }
            habit = intent.getParcelableExtra<Habits>(EXTRA_HABIT_ITEM)
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun getHabit() : Habits{
        return Habits(
            id = getHabitId(),
            title = binding.tiEtNameHabit.text.toString(),
            description = binding.tiEtDescHabit.text.toString(),
            type = getHabitType()!!,
            priorityHabit = getPriorityHabit()!!,
            numberExecutions = itemArrayExecutions!!,
            period = getHabitPeriod()!!,
            color = color
        )
    }

    private fun getHabitId(): Int {
        if (habit?.id == null){
            return habitID
        } else{
            return habit?.id!!
        }
    }

    private fun getPriorityHabit() : PriorityHabit?{
        return when(itemArrayPriority){
            PriorityHabit.HIGH.priority -> PriorityHabit.HIGH
            PriorityHabit.LOW.priority -> PriorityHabit.LOW
            PriorityHabit.MEDIUM.priority -> PriorityHabit.MEDIUM
            else -> null
        }
    }

    private fun getHabitPeriod(): PeriodExecutionHabit? {
        return when(binding.tiEtFrequency.text.toString()){
            PeriodExecutionHabit.REGULAR.period -> PeriodExecutionHabit.REGULAR
            PeriodExecutionHabit.ONE_TIME.period -> PeriodExecutionHabit.ONE_TIME
            else -> null
        }
    }

    private fun getHabitType() : TypeHabits? {
        return when(binding.tiEtTypeHabit.text.toString()){
            TypeHabits.USEFUL.type -> TypeHabits.USEFUL
            TypeHabits.HARMFUL.type ->TypeHabits.HARMFUL
            else -> null
        }
    }

    private fun openColorPickerDialog() {
        val viewDialog = LayoutInflater.from(this).inflate(R.layout.color_picker_layout, null)
        val rgb : TextView = viewDialog.findViewById(R.id.tv_rgb)
        val hsv : TextView = viewDialog.findViewById(R.id.tv_hsv)
        val colorLayout = viewDialog.findViewById<LinearLayout>(R.id.color_layout)
        val colorPicker = ColorPicker(
            widthBtn = 185,
            heightBtn = 185,
            margin = 50,
            context = this,
            colorLayout = colorLayout)
        colorPicker.createColorCard(rgb, hsv)

        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.text_choose_color))
            .setView(viewDialog)
            .setNegativeButton(resources.getString(R.string.text_close)) { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.text_gone)) { dialog, which ->
                color = colorPicker.getColorCard()
                binding.tilColorCard.setEndIconTintList(ColorStateList.valueOf(color))
                dialog.cancel()
            }
            .show()
    }

    private fun openTypeHabitDialog(){
        val singleItems = arrayOf(TypeHabits.HARMFUL.type, TypeHabits.USEFUL.type)
        val checkedItem = intArrayOf(checkedTypeNumber)
        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.text_choose_type_habit))
            .setNegativeButton(resources.getString(R.string.text_close)) { dialog, which ->
                binding.tiEtTypeHabit.text?.clear()
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.text_gone)) { dialog, which ->
                dialog.cancel()
            }
            .setSingleChoiceItems(singleItems, checkedItem[0]) { dialog, which ->
                checkedItem[0] = which
                binding.tiEtTypeHabit.setText(singleItems[which])
            }
            .show()
    }

    private fun openPeriodExecutionHabitDialog() {
        val singleItems = arrayOf(
            PeriodExecutionHabit.REGULAR.period, PeriodExecutionHabit.ONE_TIME.period)
        val checkedItem = intArrayOf(checkedPeriodNumber)
        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.text_choose_period_habit))
            .setNegativeButton(resources.getString(R.string.text_close)) { dialog, which ->
                binding.tiEtFrequency.text?.clear()
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.text_gone)) { dialog, which ->
                dialog.cancel()
            }
            .setSingleChoiceItems(singleItems, checkedItem[0]) { dialog, which ->
                checkedItem[0] = which
                binding.tiEtFrequency.setText(singleItems[which])
            }
            .show()
    }

    private fun launchAddMode() {
        binding.tvTitle.setText(R.string.text_create_habit)
    }

    private fun launchEditMode() {
        binding.tvTitle.setText(R.string.text_update_habit)
        setDataHabit()
    }

    private fun setDataHabit() {
        if (habit != null){
            binding.btSaveHabit.isEnabled = true
            binding.tiEtNameHabit.setText(habit?.title)
            binding.tiEtDescHabit.setText(habit?.description)
            binding.tvArrayPriority.setText(habit?.priorityHabit?.priority)
            binding.tvArrayExecutions.setText(habit?.numberExecutions)
            binding.tiEtTypeHabit.setText(habit?.type?.type)
            binding.tiEtFrequency.setText(habit?.period?.period)
            when(habit?.period?.period){
                PeriodExecutionHabit.REGULAR.period -> checkedPeriodNumber = 0
                PeriodExecutionHabit.ONE_TIME.period -> checkedPeriodNumber = 1
            }
            when(habit?.type?.type){
                TypeHabits.HARMFUL.type -> checkedTypeNumber = 0
                TypeHabits.USEFUL.type -> checkedTypeNumber = 1
            }
            itemArrayPriority = habit?.priorityHabit?.priority
            itemArrayExecutions = habit?.numberExecutions
            color = habit?.color!!
            binding.tilColorCard.setEndIconTintList(habit?.color?.let { ColorStateList.valueOf(it) })
        }
    }

    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_HABIT_ITEM_ID = "extra_habit_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        private const val NEW_HABIT = "new_habit"
        private const val EXTRA_HABIT_ITEM = "extra_habit_item"
        private const val UPDATE_HABIT = "update_habit"
        private const val CODE_ADD = 1
        private const val CODE_EDIT = 2
    }
}