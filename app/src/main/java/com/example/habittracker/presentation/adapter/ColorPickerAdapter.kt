package com.example.habittracker.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R

class ColorPickerAdapter : RecyclerView.Adapter<ColorPickerAdapter.ColorPickerViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ColorPickerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ColorPickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorPickerViewHolder, position: Int) {}

    override fun getItemCount() = 16

    inner class ColorPickerViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
    }
}