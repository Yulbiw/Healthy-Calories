package com.example.haelthc

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.haelthc.Fragments.DayFragment
import com.example.haelthc.Fragments.MonthFragment
import com.example.haelthc.Fragments.WeekFragment

internal class PageAdapter (fm:FragmentManager?):
        FragmentPagerAdapter(fm!!){
    override fun getItem(position: Int): Fragment {

        return when(position){
            0 -> {
                DayFragment()
            }
            1 -> {
                WeekFragment()
            }
            2 -> {
                MonthFragment()
            }
            else -> {
                DayFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3

    }

}