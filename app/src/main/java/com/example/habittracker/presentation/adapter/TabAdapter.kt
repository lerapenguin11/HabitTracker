package com.example.habittracker.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habittracker.presentation.model.Habit

class TabAdapter(
    private val fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()
    private var habitList : List<Habit> = listOf()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String/*, habits : List<Habit>*/) {
        fragmentList.add(fragment)
        titleList.add(title)
        //habitList = habits

        /*val bundle = Bundle()
        val habitArray = habitList.toTypedArray()
        bundle.putParcelableArray("HabitsList", habitArray)
        fragmentActivity.supportFragmentManager.setFragmentResult("resultList", bundle)*/
    }

    fun getTitle(position: Int): String {
        return titleList[position]
    }
}